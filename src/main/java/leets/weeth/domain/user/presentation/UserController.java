package leets.weeth.domain.user.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import leets.weeth.domain.user.application.usecase.UserManageUseCase;
import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.*;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.Response;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.SocialLoginResponse;
import static leets.weeth.domain.user.domain.entity.enums.LoginStatus.LOGIN;
import static leets.weeth.domain.user.presentation.ResponseMessage.*;

@Tag(name = "UserController", description = "사용자 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;
    private final UserManageUseCase userManageUseCase;
    private final UserGetService userGetService;

    @PostMapping("/social-login")
    public CommonResponse<SocialLoginResponse> login(@RequestBody @Valid login dto) {
        SocialLoginResponse response = userUseCase.login(dto);
        if (response.status() == LOGIN) {
            return CommonResponse.createSuccess(SOCIAL_LOGIN_SUCCESS.getMessage(), response);
        }
        return CommonResponse.createSuccess(SOCIAL_REGISTER_SUCCESS.getMessage(), response);
    }

    @PostMapping("/apply")
    public CommonResponse<Void> apply(@RequestBody @Valid SignUp dto) {
        userUseCase.apply(dto);
        return CommonResponse.createSuccess(USER_APPLY_SUCCESS.getMessage());
    }

    @GetMapping("/email")
    public CommonResponse<Boolean> checkEmail(@RequestParam String email) {
        return CommonResponse.createSuccess(USER_EMAIL_CHECK_SUCCESS.getMessage(), userGetService.check(email));
    }

    @GetMapping("/all")
    public CommonResponse<Map<Integer, List<Response>>> findAll() {
        return CommonResponse.createSuccess(USER_FIND_ALL_SUCCESS.getMessage(), userUseCase.findAll());
    }

    @GetMapping
    public CommonResponse<Response> find(@Parameter(hidden = true) @CurrentUser Long userId) {
        return CommonResponse.createSuccess(USER_FIND_BY_ID_SUCCESS.getMessage(), userUseCase.find(userId));
    }

    @PatchMapping
    public CommonResponse<Void> update(@RequestBody @Valid Update dto, @Parameter(hidden = true) @CurrentUser Long userId) {
        userUseCase.update(dto, userId);
        return CommonResponse.createSuccess(USER_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping
    public CommonResponse<Void> leave(@Parameter(hidden = true) @CurrentUser Long userId) {
        userUseCase.leave(userId);
        return CommonResponse.createSuccess(USER_LEAVE_SUCCESS.getMessage());
    }

    @PostMapping("/refresh")
    public CommonResponse<JwtDto> refresh(@Valid @RequestBody refreshRequest dto, HttpServletRequest request) {
        return CommonResponse.createSuccess(JWT_REFRESH_SUCCESS.getMessage(), userManageUseCase.refresh(dto, request));
    }
}
