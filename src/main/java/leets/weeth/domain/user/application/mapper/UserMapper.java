package leets.weeth.domain.user.application.mapper;

import leets.weeth.domain.user.application.dto.response.UserResponseDto.SummaryResponse;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.enums.Department;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.SignUp;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.AdminResponse;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.Response;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "cardinals", expression = "java( java.util.List.of(dto.cardinal()) )"),
            @Mapping(target = "password", expression = "java( passwordEncoder.encode(dto.password()) )"),
            @Mapping(target = "department", expression = "java( leets.weeth.domain.user.domain.entity.enums.Department.to(dto.department()) )")
    })
    User from(SignUp dto, @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "department", expression = "java( toString(user.getDepartment()) )")
    Response to(User user);

    @Mappings({
            // 수정: 출석률, 출석 횟수, 결석 횟수 매핑 추후 추가 예정
    })
    AdminResponse toAdminResponse(User user);
    @Mappings({
            @Mapping(target = "department", expression = "java( toString(user.getDepartment()) )")
    })
    SummaryResponse toSummaryResponse(User user);

    @Mappings({
            // 상세 데이터 매핑
    })
    Response toResponse(User user);
    default String toString(Department department) {
        return department.getValue();
    }
}

