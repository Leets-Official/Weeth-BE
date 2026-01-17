package leets.weeth.global.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.user.application.exception.UserErrorCode;
import leets.weeth.global.common.exception.ApiErrorCodeExample;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exceptions")
@Tag(name = "Exception Document", description = "API 에러 코드 문서")
public class ExceptionDocController {

    @GetMapping("/user")
    @Operation(summary = "User 도메인 에러 코드 목록")
    @ApiErrorCodeExample(UserErrorCode.class)
    public void userErrorCodes() {
    }
}