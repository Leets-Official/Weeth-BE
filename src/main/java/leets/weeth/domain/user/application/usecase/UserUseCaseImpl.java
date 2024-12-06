package leets.weeth.domain.user.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import leets.weeth.domain.user.application.dto.request.UserRequestDto;
import leets.weeth.domain.user.application.dto.response.UserResponseDto;
import leets.weeth.domain.user.application.exception.StudentIdExistsException;
import leets.weeth.domain.user.application.exception.TelExistsException;
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

import static leets.weeth.domain.user.domain.entity.enums.LoginStatus.LOGIN;
import static leets.weeth.domain.user.domain.entity.enums.LoginStatus.REGISTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {
    private final JwtManageUseCase jwtManageUseCase;
    private final UserSaveService userSaveService;
    private final UserGetService userGetService;
    private final UserUpdateService userUpdateService;
    private final KakaoAuthService kakaoAuthService;

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto.SocialLoginResponse login(UserRequestDto.login dto) {
        KakaoTokenResponse tokenResponse = kakaoAuthService.getKakaoToken(dto.authCode());
        KakaoUserInfoResponse userInfo = kakaoAuthService.getUserInfo(tokenResponse.access_token());

        String email = userInfo.kakao_account().email();

        if (existUser(email)) {
            return login(email);
        }
        return registerUser(email);
    }

    public boolean existUser(String email) {
        return !userGetService.check(email);
    }

    private UserResponseDto.SocialLoginResponse registerUser(String email) {
        User user = User.builder()
                .email(email)
                .build();
        userSaveService.save(user);

        JwtDto dto = jwtManageUseCase.create(user.getId(), email, user.getRole());

        return new UserResponseDto.SocialLoginResponse(user.getId(), REGISTER, dto.accessToken(), dto.refreshToken());
    }

    private UserResponseDto.SocialLoginResponse login(String email) {
        User user = userGetService.find(email);

        JwtDto dto = jwtManageUseCase.create(user.getId(), email, user.getRole());

        return new UserResponseDto.SocialLoginResponse(user.getId(), LOGIN, dto.accessToken(), dto.refreshToken());
    }

    @Override
    public void apply(UserRequestDto.SignUp dto) {
        validate(dto);
        userSaveService.save(mapper.from(dto, passwordEncoder));
    }

    @Override
    @Transactional
    public void register(UserRequestDto.Register dto, Long userId) {
        validate(dto, userId);
        User user = userGetService.find(userId);
        userUpdateService.update(user, dto);
    }

    @Override
    @Transactional
    public JwtDto refresh(HttpServletRequest request) {

        JwtDto token = jwtManageUseCase.reIssueToken(request);

        log.info("RefreshToken 발급 완료: {}", token);
        return new JwtDto(token.accessToken(), token.refreshToken());
    }

    private void validate(UserRequestDto.SignUp dto) {
        if (userGetService.validateStudentId(dto.studentId()))
            throw new StudentIdExistsException();
        if (userGetService.validateTel(dto.tel()))
            throw new TelExistsException();
    }

    private void validate(UserRequestDto.Register dto, Long userId) {
        if (userGetService.validateStudentId(dto.studentId(), userId)) {
            throw new StudentIdExistsException();
        }
        if (userGetService.validateTel(dto.tel(), userId)) {
            throw new TelExistsException();
        }
    }

}
