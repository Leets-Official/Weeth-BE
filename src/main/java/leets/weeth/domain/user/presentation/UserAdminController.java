package leets.weeth.domain.user.presentation;

import leets.weeth.domain.user.application.dto.UserDTO;
import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
