package leets.weeth.domain.user.presentation;

import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static leets.weeth.domain.user.application.dto.UserDTO.AdminResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class UserAdminController {

    private final UserUseCase userUseCase;

    @GetMapping("/all")
    public CommonResponse<List<AdminResponse>> findAll() {
        return CommonResponse.createSuccess(userUseCase.findAllByAdmin());
    }

    @PatchMapping
    public CommonResponse<Void> accept(@RequestParam Long userId) {
        userUseCase.accept(userId);
        return CommonResponse.createSuccess();
    }

    @DeleteMapping
    public CommonResponse<Void> ban(@RequestParam Long userId) {
        userUseCase.ban(userId);
        return CommonResponse.createSuccess();
    }

    @PatchMapping("/role")
    public CommonResponse<Void> update(@RequestParam Long userId, @RequestParam String role) {
        userUseCase.update(userId, role);
        return CommonResponse.createSuccess();
    }

    @PatchMapping("/apply")
    public CommonResponse<Void> applyOB(@RequestParam Long userId, @RequestParam Integer cardinal) {
        userUseCase.applyOB(userId, cardinal);
        return CommonResponse.createSuccess();
    }

    @PatchMapping("/reset")
    public CommonResponse<Void> resetPassword(@RequestParam Long userId) {
        userUseCase.reset(userId);
        return CommonResponse.createSuccess();
    }
}
