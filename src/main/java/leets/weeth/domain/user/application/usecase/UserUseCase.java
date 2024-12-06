package leets.weeth.domain.user.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import leets.weeth.domain.user.application.dto.request.UserRequestDto;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.*;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.*;


public interface UserUseCase {

    SocialLoginResponse login(UserRequestDto.login dto);

    void apply(SignUp dto);

    void register(Register dto, Long userId);

    JwtDto refresh(String refreshToken);

}
