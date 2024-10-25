package leets.weeth.domain.user.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.UserDTO.*;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_APPLY_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_EMAIL_CHECK_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_FIND_ALL_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_FIND_BY_ID_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_LEAVE_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.USER_UPDATE_SUCCESS;

@Tag(name = "UserController", description = "사용자 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;
    private final UserGetService userGetService;

    @PostMapping("/apply")
    public CommonResponse<Void> apply(@RequestBody @Valid SignUp dto) {
        userUseCase.apply(dto);
        return CommonResponse.createSuccess(USER_APPLY_SUCCESS.getMessage());
    }

    @GetMapping("/email")
    public CommonResponse<Boolean> checkEmail(@RequestParam String email) {
        return CommonResponse.createSuccess(USER_EMAIL_CHECK_SUCCESS.getMessage(),userGetService.check(email));
    }

    @GetMapping("/all")
    public CommonResponse<Map<Integer, List<Response>>> findAll() {
        return CommonResponse.createSuccess(USER_FIND_ALL_SUCCESS.getMessage(),userUseCase.findAll());
    }

    @GetMapping
    public CommonResponse<Response> find(@CurrentUser Long userId) {
        return CommonResponse.createSuccess(USER_FIND_BY_ID_SUCCESS.getMessage(),userUseCase.find(userId));
    }

    @PatchMapping
    public CommonResponse<Void> update(@RequestBody @Valid Update dto, @CurrentUser Long userId) {
        userUseCase.update(dto, userId);
        return CommonResponse.createSuccess(USER_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping
    public CommonResponse<Void> leave(@CurrentUser Long userId) {
        userUseCase.leave(userId);
        return CommonResponse.createSuccess(USER_LEAVE_SUCCESS.getMessage());
    }
}
