package leets.weeth.domain.user.presentation;

import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static leets.weeth.domain.user.application.dto.UserDTO.AdminResponse;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_ACCEPT_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_APPLY_OB_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_BAN_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_FIND_ALL_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_PASSWORD_RESET_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_ROLE_UPDATE_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class UserAdminController {

    private final UserUseCase userUseCase;

    @GetMapping("/all")
    public CommonResponse<List<AdminResponse>> findAll() {
        return CommonResponse.createSuccess(USER_FIND_ALL_SUCCESS.getStatusCode(),
                USER_FIND_ALL_SUCCESS.getMessage(),userUseCase.findAllByAdmin());
    }

    @PatchMapping
    public CommonResponse<Void> accept(@RequestParam Long userId) {
        userUseCase.accept(userId);
        return CommonResponse.createSuccess(USER_ACCEPT_SUCCESS.getStatusCode(),
                USER_ACCEPT_SUCCESS.getMessage());
    }

    @DeleteMapping
    public CommonResponse<Void> ban(@RequestParam Long userId) {
        userUseCase.ban(userId);
        return CommonResponse.createSuccess(USER_BAN_SUCCESS.getStatusCode(),
                USER_BAN_SUCCESS.getMessage());
    }

    @PatchMapping("/role")
    public CommonResponse<Void> update(@RequestParam Long userId, @RequestParam String role) {
        userUseCase.update(userId, role);
        return CommonResponse.createSuccess(USER_ROLE_UPDATE_SUCCESS.getStatusCode(),
                USER_ROLE_UPDATE_SUCCESS.getMessage());
    }

    @PatchMapping("/apply")
    public CommonResponse<Void> applyOB(@RequestParam Long userId, @RequestParam Integer cardinal) {
        userUseCase.applyOB(userId, cardinal);
        return CommonResponse.createSuccess(USER_APPLY_OB_SUCCESS.getStatusCode(),
                USER_APPLY_OB_SUCCESS.getMessage());
    }

    @PatchMapping("/reset")
    public CommonResponse<Void> resetPassword(@RequestParam Long userId) {
        userUseCase.reset(userId);
        return CommonResponse.createSuccess(USER_PASSWORD_RESET_SUCCESS.getStatusCode(),
                USER_PASSWORD_RESET_SUCCESS.getMessage());
    }
}
