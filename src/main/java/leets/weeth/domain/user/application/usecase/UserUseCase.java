package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.request.UserRequestDto;
import leets.weeth.domain.user.application.dto.response.UserResponseDto;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.Register;
import static leets.weeth.domain.user.application.dto.request.UserRequestDto.SignUp;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.SocialLoginResponse;


public interface UserUseCase {

    SocialLoginResponse login(UserRequestDto.login dto);

    UserResponseDto.Response find(Long userId);

    Map<Integer, List<UserResponseDto.Response>> findAll();

    Map<Integer, List<UserResponseDto.SummaryResponse>> findAllUser();

    UserResponseDto.UserResponse findUserDetails(Long userId);

    void update(UserRequestDto.Update dto, Long userId);

    void apply(SignUp dto);

    void register(Register dto, Long userId);

    JwtDto refresh(String refreshToken);

}
