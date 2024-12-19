package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.request.UserRequestDto.Login;
import leets.weeth.domain.user.application.dto.request.UserRequestDto.Register;
import leets.weeth.domain.user.application.dto.request.UserRequestDto.SignUp;
import leets.weeth.domain.user.application.exception.PasswordMismatchException;
import leets.weeth.domain.user.application.exception.StudentIdExistsException;
import leets.weeth.domain.user.application.exception.TelExistsException;
import leets.weeth.domain.user.application.exception.UserInActiveException;
import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.domain.user.domain.service.UserSaveService;
import leets.weeth.domain.user.domain.service.UserUpdateService;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;
import leets.weeth.global.auth.jwt.application.usecase.JwtManageUseCase;
import leets.weeth.global.auth.kakao.KakaoAuthService;
import leets.weeth.global.auth.kakao.dto.KakaoTokenResponse;
import leets.weeth.global.auth.kakao.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.NormalLogin;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.SocialAuthResponse;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.SocialLoginResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {
    private static final String BEARER = "Bearer ";
    private final JwtManageUseCase jwtManageUseCase;
    private final UserSaveService userSaveService;
    private final UserGetService userGetService;
    private final UserUpdateService userUpdateService;
    private final KakaoAuthService kakaoAuthService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public SocialLoginResponse login(Login dto) {
        long kakaoId = getKakaoId(dto);
        User user = userGetService.find(kakaoId);

        if (user == null) {
            return mapper.toIntegrateResponse(kakaoId);
        }

        if (user.isInactive()) {
            throw new UserInActiveException();
        }

        JwtDto token = jwtManageUseCase.create(user.getId(), user.getEmail(), user.getRole());
        return mapper.toLoginResponse(user, token);
    }

    @Override
    public SocialAuthResponse authenticate(Login dto) {
        long kakaoId = getKakaoId(dto);

        return mapper.toSocialAuthResponse(kakaoId);
    }

    @Override
    @Transactional
    public SocialLoginResponse integrate(NormalLogin dto) {
        User user = userGetService.find(dto.email());

        if (!passwordEncoder.matches(dto.passWord(), user.getPassword())) {
            throw new PasswordMismatchException();
        }
        user.addKakaoId(dto.kakaoId());

        if (user.isInactive()) {
            throw new UserInActiveException();
        }

        JwtDto token = jwtManageUseCase.create(user.getId(), user.getEmail(), user.getRole());

        return mapper.toLoginResponse(user, token);
    }

    @Override
    public void apply(SignUp dto) {
        validate(dto);
        userSaveService.save(mapper.from(dto, passwordEncoder));
    }

    @Override
    @Transactional
    public void register(Register dto) {
        validate(dto);
        userSaveService.save(mapper.from(dto));
    }

    @Override
    @Transactional
    public JwtDto refresh(String refreshToken) {

        String requestToken = refreshToken.replace(BEARER, "");

        JwtDto token = jwtManageUseCase.reIssueToken(requestToken);

        log.info("RefreshToken 발급 완료: {}", token);
        return new JwtDto(token.accessToken(), token.refreshToken());
    }

    private long getKakaoId(Login dto) {
        KakaoTokenResponse tokenResponse = kakaoAuthService.getKakaoToken(dto.authCode());
        KakaoUserInfoResponse userInfo = kakaoAuthService.getUserInfo(tokenResponse.access_token());

        return userInfo.id();
    }

    private void validate(SignUp dto) {
        if (userGetService.validateStudentId(dto.studentId()))
            throw new StudentIdExistsException();
        if (userGetService.validateTel(dto.tel()))
            throw new TelExistsException();
    }

    private void validate(Register dto) {
        if (userGetService.validateStudentId(dto.studentId())) {
            throw new StudentIdExistsException();
        }
        if (userGetService.validateTel(dto.tel())) {
            throw new TelExistsException();
        }
    }

}
