package leets.weeth.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.weeth.domain.user.application.dto.response.UserResponseDto;
import leets.weeth.domain.user.application.dto.response.UserResponseDto.SummaryResponse;
import leets.weeth.domain.user.application.dto.response.UserResponseDto.UserResponse;
import leets.weeth.domain.user.application.usecase.UserManageUseCase;
import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.*;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.Response;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.SocialLoginResponse;
import static leets.weeth.domain.user.presentation.ResponseMessage.*;

@Tag(name = "USER", description = "사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;
    private final UserManageUseCase userManageUseCase;
    private final UserGetService userGetService;

    @PostMapping("/kakao/login")
    @Operation(summary = "카카오 소셜 로그인 API")
    public CommonResponse<SocialLoginResponse> login(@RequestBody @Valid Login dto) {
        SocialLoginResponse response = userUseCase.login(dto);
        return CommonResponse.createSuccess(SOCIAL_LOGIN_SUCCESS.getMessage(), response);
    }

    @PostMapping("/kakao/auth")
    @Operation(summary = "카카오 소셜 회원가입 전 요청 API (미사용 API)")
    public CommonResponse<UserResponseDto.SocialAuthResponse> beforeRegister(@RequestBody @Valid Login dto) {
        UserResponseDto.SocialAuthResponse response = userUseCase.authenticate(dto);
        return CommonResponse.createSuccess(SOCIAL_AUTH_SUCCESS.getMessage(), response);
    }

    @PostMapping("/apply")
    @Operation(summary = "동아리 지원 신청. 현재 사용하지 않으므로 회원가입 시 /kakao/register api로 요청 바람")
    public CommonResponse<Void> apply(@RequestBody @Valid SignUp dto) {
        userUseCase.apply(dto);
        return CommonResponse.createSuccess(USER_APPLY_SUCCESS.getMessage());
    }

    @PostMapping("/kakao/register")
    @Operation(summary = "소셜 회원가입")
    public CommonResponse<Void> register(@RequestBody @Valid Register dto) {
        userUseCase.socialRegister(dto);
        return CommonResponse.createSuccess(USER_APPLY_SUCCESS.getMessage());
    }

    @PatchMapping("/kakao/link")
    @Operation(summary = "카카오 소셜 로그인 연동")
    public CommonResponse<SocialLoginResponse> integrate(@RequestBody @Valid NormalLogin dto) {
        return CommonResponse.createSuccess(SOCIAL_INTEGRATE_SUCCESS.getMessage(), userUseCase.integrate(dto));
    }

    @GetMapping("/email")
    @Operation(summary = "이메일 중복 확인")
    public CommonResponse<Boolean> checkEmail(@RequestParam String email) {
        return CommonResponse.createSuccess(USER_EMAIL_CHECK_SUCCESS.getMessage(), userGetService.check(email));
    }

    @GetMapping("/all")
    @Operation(summary = "동아리 멤버 전체 조회(전체/기수별)")
    public CommonResponse<Slice<SummaryResponse>> findAllUser(@RequestParam("pageNumber") int pageNumber,
                                                              @RequestParam("pageSize") int pageSize,
                                                              @RequestParam(required = false) Integer cardinal) {
        return CommonResponse.createSuccess(USER_FIND_ALL_SUCCESS.getMessage(), userUseCase.findAllUser(pageNumber, pageSize, cardinal));
    }

    @GetMapping("/search")
    @Operation(summary = "동아리 멤버 검색")
    public CommonResponse<List<SummaryResponse>> searchUser(@RequestParam String keyword) {
        return CommonResponse.createSuccess(USER_FIND_BY_ID_SUCCESS.getMessage(), userUseCase.searchUser(keyword));
    }

    @GetMapping("/details")
    @Operation(summary = "특정 멤버 상세 조회")
    public CommonResponse<UserResponse> findUser(@RequestParam Long userId) {
        return CommonResponse.createSuccess(
                USER_DETAILS_SUCCESS.getMessage(), userUseCase.findUserDetails(userId)
        );
    }

    @GetMapping
    @Operation(summary = "내 정보 조회")
    public CommonResponse<Response> find(@Parameter(hidden = true) @CurrentUser Long userId) {
        return CommonResponse.createSuccess(USER_FIND_BY_ID_SUCCESS.getMessage(), userUseCase.find(userId));
    }

    @GetMapping("/info")
    @Operation(summary = "전역 내 정보 조회 API")
    public CommonResponse<UserResponseDto.UserInfo> findMyInfo(@Parameter(hidden = true) @CurrentUser Long userId) {
        return CommonResponse.createSuccess(USER_FIND_BY_ID_SUCCESS.getMessage(), userUseCase.findUserInfo(userId));
    }

    @PatchMapping
    @Operation(summary = "내 정보 수정")
    public CommonResponse<Void> update(@RequestBody @Valid Update dto, @Parameter(hidden = true) @CurrentUser Long userId) {
        userUseCase.update(dto, userId);
        return CommonResponse.createSuccess(USER_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping
    @Operation(summary = "동아리 탈퇴")
    public CommonResponse<Void> leave(@Parameter(hidden = true) @CurrentUser Long userId) {
        userManageUseCase.leave(userId);
        return CommonResponse.createSuccess(USER_LEAVE_SUCCESS.getMessage());
    }

    @PostMapping("/refresh")
    @Operation(summary = "JWT 토큰 재발급 API")
    public CommonResponse<JwtDto> refresh(@Parameter(hidden = true) @RequestHeader("Authorization_refresh") String refreshToken) {
        return CommonResponse.createSuccess(JWT_REFRESH_SUCCESS.getMessage(), userUseCase.refresh(refreshToken));
    }
}
