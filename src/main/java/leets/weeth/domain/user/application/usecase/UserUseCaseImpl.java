package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.response.UserResponseDto;
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

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.*;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.SocialAuthResponse;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.SocialLoginResponse;
import static leets.weeth.domain.user.domain.entity.enums.Status.ACTIVE;

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
        Optional<User> optionalUser = userGetService.find(kakaoId);

        if (optionalUser.isEmpty()) {
            return mapper.toIntegrateResponse(kakaoId);
        }

        User user = optionalUser.get();
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
    public Map<Integer, List<UserResponseDto.Response>> findAll() {
        return userGetService.findAllByStatus(ACTIVE).stream()
                .flatMap(user -> Stream.concat(
                        user.getCardinals().stream()
                                .map(cardinal -> new AbstractMap.SimpleEntry<>(cardinal, mapper.to(user))), // 기수별 Map
                        Stream.of(new AbstractMap.SimpleEntry<>(0, mapper.to(user)))    // 모든 기수는 cardinal 0에 저장
                ))
                .collect(Collectors.groupingBy(Map.Entry::getKey,   // key = 기수, value = 유저 정보
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    @Override
    public Map<Integer, List<UserResponseDto.SummaryResponse>> findAllUser() {
        return userGetService.findAllByStatus(ACTIVE).stream()
                .map(user -> new AbstractMap.SimpleEntry<>(user.getCardinals(), mapper.toSummaryResponse(user)))
                .flatMap(entry -> Stream.concat(
                        entry.getKey().stream().map(cardinal -> new AbstractMap.SimpleEntry<>(cardinal, entry.getValue())), // 기수별 Map
                        Stream.of(new AbstractMap.SimpleEntry<>(0, entry.getValue())) // 모든 기수는 cardinal 0에 저장
                ))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey, // key = 기수
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList()) // value = 요약 정보 리스트
                ));
    }

    @Override
    public UserResponseDto.UserResponse findUserDetails(Long userId) {
        User user = userGetService.find(userId);
        return mapper.toUserResponse(user);
    }

    @Override
    public UserResponseDto.Response find(Long userId) {
        return mapper.to(userGetService.find(userId));
    }

    @Override
    public void update(Update dto, Long userId) {
        validate(dto, userId);
        User user = userGetService.find(userId);
        userUpdateService.update(user, dto, passwordEncoder);
    }

    @Override
    public void apply(SignUp dto) {
        validate(dto);
        userSaveService.save(mapper.from(dto, passwordEncoder));
    }

    @Override
    @Transactional
    public void socialRegister(Register dto) {
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

    @Override
    public UserResponseDto.UserInfo findUserInfo(Long userId) {
        User user = userGetService.find(userId);

        return mapper.toUserInfoDto(user);
    }
    private long getKakaoId(Login dto) {
        KakaoTokenResponse tokenResponse = kakaoAuthService.getKakaoToken(dto.authCode());
        KakaoUserInfoResponse userInfo = kakaoAuthService.getUserInfo(tokenResponse.access_token());

        return userInfo.id();
    }

    private void validate(Update dto, Long userId) {
        if (userGetService.validateStudentId(dto.studentId(), userId))
            throw new StudentIdExistsException();
        if (userGetService.validateTel(dto.tel(), userId))
            throw new TelExistsException();
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
