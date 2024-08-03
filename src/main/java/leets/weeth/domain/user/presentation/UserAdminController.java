package leets.weeth.domain.user.presentation;

import leets.weeth.domain.user.application.dto.UserDTO;
import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static leets.weeth.domain.user.application.dto.UserDTO.*;

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


}
