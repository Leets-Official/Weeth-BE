package leets.weeth.global.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import leets.weeth.global.common.exception.ApiErrorCodeExample;
import leets.weeth.global.common.exception.ApiErrorExceptionsExample;
import leets.weeth.global.common.exception.ErrorCodeInterface;
import leets.weeth.global.common.exception.ExampleHolder;
import leets.weeth.global.common.exception.ExplainError;
import leets.weeth.global.common.exception.BusinessLogicException;
import leets.weeth.global.common.response.CommonResponse;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Weeth API",
                description = "Weeth API 명세서",
                version = "v1.0.0"
        )
)
public class SwaggerConfig {

    @Value("${weeth.jwt.access.header}")
    private String accessHeader;

    @Value("${weeth.jwt.refresh.header}")
    private String refreshHeader;

    private final ApplicationContext applicationContext;

    public SwaggerConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme accessSecurityScheme = getAccessSecurityScheme();
        SecurityScheme refreshSecurityScheme = getRefreshSecurityScheme();

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", accessSecurityScheme)
                        .addSecuritySchemes("refreshBearerAuth", refreshSecurityScheme))
                .security(List.of(
                        new SecurityRequirement().addList("bearerAuth"),
                        new SecurityRequirement().addList("refreshBearerAuth")
                ));
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            // 메서드 레벨 어노테이션 우선, 없으면 클래스 레벨 체크
            ApiErrorCodeExample apiErrorCodeExample = handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);
            if (apiErrorCodeExample == null) {
                apiErrorCodeExample = handlerMethod.getBeanType().getAnnotation(ApiErrorCodeExample.class);
            }

            ApiErrorExceptionsExample apiErrorExceptionsExample = handlerMethod.getMethodAnnotation(ApiErrorExceptionsExample.class);

            if (apiErrorCodeExample != null) {
                generateErrorCodeResponseExample(operation.getResponses(), apiErrorCodeExample.value());
            }

            if (apiErrorExceptionsExample != null) {
                generateExceptionResponseExample(operation.getResponses(), apiErrorExceptionsExample.value());
            }

            return operation;
        };
    }

    private void generateErrorCodeResponseExample(ApiResponses responses, Class<? extends ErrorCodeInterface> type) {
        ErrorCodeInterface[] errorCodes = type.getEnumConstants();

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders =
                Arrays.stream(errorCodes)
                        .map(errorCode -> {
                            try {
                                return ExampleHolder.builder()
                                        .holder(getSwaggerExample(errorCode.getExplainError(), errorCode))
                                        .code(errorCode.getStatus().value())
                                        .name(errorCode.getMessage())
                                        .build();
                            } catch (NoSuchFieldException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(groupingBy(ExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private void generateExceptionResponseExample(ApiResponses responses, Class<?> type) {
        Object bean = applicationContext.getBean(type);
        Field[] declaredFields = bean.getClass().getDeclaredFields();

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders =
                Arrays.stream(declaredFields)
                        .filter(field -> field.getAnnotation(ExplainError.class) != null)
                        .filter(field -> BusinessLogicException.class.isAssignableFrom(field.getType()))
                        .map(field -> {
                            try {
                                field.setAccessible(true);
                                BusinessLogicException exception = (BusinessLogicException) field.get(bean);
                                ExplainError annotation = field.getAnnotation(ExplainError.class);
                                String description = annotation.value();
                                ErrorCodeInterface errorCode = exception.getErrorCode();

                                return ExampleHolder.builder()
                                        .holder(getSwaggerExample(description, errorCode))
                                        .code(exception.getStatusCode())
                                        .name(field.getName())
                                        .build();
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(groupingBy(ExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private Example getSwaggerExample(String description, ErrorCodeInterface errorCode) {
        CommonResponse<Void> errorResponse = CommonResponse.createFailure(errorCode.getCode(), errorCode.getMessage());
        Example example = new Example();
        example.description(description);
        example.setValue(errorResponse);
        return example;
    }

    private void addExamplesToResponses(ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach((status, exampleHolders) -> {
            Content content = new Content();
            MediaType mediaType = new MediaType();
            ApiResponse apiResponse = new ApiResponse();

            exampleHolders.forEach(holder -> mediaType.addExamples(holder.getName(), holder.getHolder()));

            content.addMediaType("application/json", mediaType);
            apiResponse.setContent(content);
            responses.addApiResponse(status.toString(), apiResponse);
        });
    }

    private SecurityScheme getAccessSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(accessHeader);
    }

    private SecurityScheme getRefreshSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(refreshHeader);
    }
}
