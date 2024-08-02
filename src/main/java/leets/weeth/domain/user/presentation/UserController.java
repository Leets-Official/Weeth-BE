package leets.weeth.domain.user.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.weeth.domain.user.application.usecase.UserUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static leets.weeth.domain.user.application.dto.UserDTO.SignUp;

@Tag(name = "UserController", description = "사용자 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;

    @PostMapping
    public CommonResponse<Void> apply(@RequestBody @Valid SignUp dto) {
        userUseCase.apply(dto);
        return CommonResponse.createSuccess();
    }



//    @Operation(summary = "동아리 가입 신청")
//    @PostMapping("/apply")
//    public CommonResponse<String> apply(@RequestBody @Valid UserDTO.SignUp requestDto) {
//        userService.signUp(requestDto);
//        return CommonResponse.createSuccess();
//    }
//
//    @Operation(summary = "동아리 탈퇴")
//    @DeleteMapping("")
//    public CommonResponse<String> delete(@CurrentUser Long userId) {
//        userService.delete(userId);
//        return CommonResponse.createSuccess();
//    }
//
//    @Operation(summary = "동아리 회원 조회 (전체/기수별)")
//    @GetMapping("/all")
//    public CommonResponse<Map<Integer, List<UserDTO.Response>>> getUsers() {
//        return CommonResponse.createSuccess(userService.findAll());
//    }
//
//    @Operation(summary = "내 정보 조회")
//    @GetMapping
//    public CommonResponse<UserDTO.Response> getUser(@CurrentUser Long userId) {
//        return CommonResponse.createSuccess(userService.find(userId));
//    }
//
//    @Operation(summary = "내 정보 수정")
//    @PatchMapping
//    public CommonResponse<Void> update(@CurrentUser Long userId, @RequestBody @Valid UserDTO.Update dto) {
//        userService.update(userId, dto);
//        return CommonResponse.createSuccess();
//    }
//
//    @Operation(summary = "이메일 중복 조회")
//    @GetMapping("/duplication/{email}")
//    public CommonResponse<String> validate(@PathVariable String email) {
//        userService.validate(email);
//        return CommonResponse.createSuccess();
//    }
}
