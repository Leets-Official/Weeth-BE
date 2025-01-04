package leets.weeth.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.user.application.usecase.UserManageUseCase;
import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.response.UserResponseDto.AdminResponse;
import static leets.weeth.domain.user.presentation.ResponseMessage.*;

@Tag(name = "USER ADMIN", description = "[ADMIN] 사용자 어드민 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class UserAdminController {

    private final UserManageUseCase userManageUseCase;

    @GetMapping("/all")
    @Operation(summary = "어드민용 회원 조회")
    public CommonResponse<Map<Integer, List<AdminResponse>>> findAll() {
        // to do : 추후 기수 분리 후 작업 예정
        List <AdminResponse> usersByCardinal = new ArrayList<>();
        List<AdminResponse> usersByName = userManageUseCase.findAllByAdmin();

        return CommonResponse.createSuccess(USER_FIND_ALL_SUCCESS.getMessage(), Map.of(0, usersByCardinal,
                1, usersByName));
    }

    @PatchMapping
    @Operation(summary="가입 신청 승인")
    public CommonResponse<Void> accept(@RequestParam Long userId) {
        userManageUseCase.accept(userId);
        return CommonResponse.createSuccess(USER_ACCEPT_SUCCESS.getMessage());
    }

    @DeleteMapping
    @Operation(summary="유저 추방")
    public CommonResponse<Void> ban(@RequestParam Long userId) {
        userManageUseCase.ban(userId);
        return CommonResponse.createSuccess(USER_BAN_SUCCESS.getMessage());
    }

    @PatchMapping("/role")
    @Operation(summary="관리자로 승격/강등")
    public CommonResponse<Void> update(@RequestParam Long userId, @RequestParam String role) {
        userManageUseCase.update(userId, role);
        return CommonResponse.createSuccess(USER_ROLE_UPDATE_SUCCESS.getMessage());
    }

    @PatchMapping("/apply")
    @Operation(summary="다음 기수도 이어서 진행")
    public CommonResponse<Void> applyOB(@RequestParam Long userId, @RequestParam Integer cardinal) {
        userManageUseCase.applyOB(userId, cardinal);
        return CommonResponse.createSuccess(USER_APPLY_OB_SUCCESS.getMessage());
    }

    @PatchMapping("/reset")
    @Operation(summary="회원 비밀번호 초기화")
    public CommonResponse<Void> resetPassword(@RequestParam Long userId) {
        userManageUseCase.reset(userId);
        return CommonResponse.createSuccess(USER_PASSWORD_RESET_SUCCESS.getMessage());
    }
}
