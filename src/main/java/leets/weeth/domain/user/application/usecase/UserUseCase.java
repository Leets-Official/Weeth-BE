package leets.weeth.domain.user.application.usecase;

import leets.weeth.global.auth.jwt.application.dto.JwtDto;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.*;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.*;


public interface UserUseCase {

    SocialLoginResponse login(Login dto);

    SocialAuthResponse authenticate(Login dto);

    SocialLoginResponse integrate(NormalLogin dto);

    void apply(SignUp dto);

    void register(Register dto);

    JwtDto refresh(String refreshToken);
}
