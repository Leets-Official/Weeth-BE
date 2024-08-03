package leets.weeth.domain.user.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.weeth.domain.user.application.dto.UserDTO;
import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.UserDTO.SignUp;

@Tag(name = "UserController", description = "사용자 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;
    private final UserGetService userGetService;

    @PostMapping
    public CommonResponse<Void> apply(@RequestBody @Valid SignUp dto) {
        userUseCase.apply(dto);
        return CommonResponse.createSuccess();
    }

    @GetMapping("/email")
    public CommonResponse<Boolean> checkEmail(@RequestParam String email) {
        return CommonResponse.createSuccess(userGetService.check(email));
    }

    @GetMapping("/all")
    public CommonResponse<Map<Integer, List<UserDTO.Response>>> findAll() {
        return CommonResponse.createSuccess(userUseCase.findAll());
    }
}
