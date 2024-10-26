package leets.weeth.domain.user.presentation;

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
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.*;


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
        return CommonResponse.createSuccess(userUseCase.login(dto));
    }

    @PostMapping("/apply")
    public CommonResponse<Void> apply(@RequestBody @Valid SignUp dto) {
        userUseCase.apply(dto);
        return CommonResponse.createSuccess();
    }

    @GetMapping("/email")
    public CommonResponse<Boolean> checkEmail(@RequestParam String email) {
        return CommonResponse.createSuccess(userGetService.check(email));
    }

    @GetMapping("/all")
    public CommonResponse<Map<Integer, List<Response>>> findAll() {
        return CommonResponse.createSuccess(userUseCase.findAll());
    }

    @GetMapping
    public CommonResponse<Response> find(@CurrentUser Long userId) {
        return CommonResponse.createSuccess(userUseCase.find(userId));
    }

    @PatchMapping
    public CommonResponse<Void> update(@RequestBody @Valid Update dto, @CurrentUser Long userId) {
        userUseCase.update(dto, userId);
        return CommonResponse.createSuccess();
    }

    @DeleteMapping
    public CommonResponse<Void> leave(@CurrentUser Long userId) {
        userUseCase.leave(userId);
        return CommonResponse.createSuccess();
    }

    @PostMapping("/refresh")
    public CommonResponse<JwtDto> refresh(@Valid @RequestBody refreshRequest dto, HttpServletRequest request) {
        return CommonResponse.createSuccess(userManageUseCase.refresh(dto, request));
    }
}
