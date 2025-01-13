package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.response.UserCardinalDto;
import leets.weeth.domain.user.application.dto.response.UserResponseDto;
import leets.weeth.domain.user.application.exception.PasswordMismatchException;
import leets.weeth.domain.user.application.exception.StudentIdExistsException;
import leets.weeth.domain.user.application.exception.TelExistsException;
import leets.weeth.domain.user.application.exception.UserInActiveException;
import leets.weeth.domain.user.application.mapper.CardinalMapper;
import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.entity.Cardinal;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.UserCardinal;
import leets.weeth.domain.user.domain.service.*;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;
import leets.weeth.global.auth.jwt.application.usecase.JwtManageUseCase;
import leets.weeth.global.auth.kakao.KakaoAuthService;
import leets.weeth.global.auth.kakao.dto.KakaoTokenResponse;
import leets.weeth.global.auth.kakao.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.*;
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
    private final CardinalGetService cardinalGetService;
    private final UserCardinalSaveService userCardinalSaveService;
    private final UserCardinalGetService userCardinalGetService;

    private final UserMapper mapper;
    private final CardinalMapper cardinalMapper;
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
    public Slice<UserResponseDto.SummaryResponse> findAllUser(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Slice<User> users = userGetService.findAll(pageable);

        List<UserCardinal> allUserCardinals = userCardinalGetService.findAll(users.getContent());

        Map<Long, List<UserCardinal>> userCardinalMap = allUserCardinals.stream()
                .collect(Collectors.groupingBy(userCardinal -> userCardinal.getUser().getId()));

        return users.map(user -> {
            List<UserCardinal> userCardinals = userCardinalMap.getOrDefault(user.getId(), Collections.emptyList());

            return mapper.toSummaryResponse(user, userCardinals);
        });
    }

    @Override
    public UserResponseDto.UserResponse findUserDetails(Long userId) {
        UserCardinalDto dto = getUserCardinalDto(userId);

        return mapper.toUserResponse(dto.user(), dto.cardinals());
    }

    @Override
    public UserResponseDto.Response find(Long userId) {
        UserCardinalDto dto = getUserCardinalDto(userId);

        return mapper.to(dto.user(), dto.cardinals());
    }

    @Override
    public void update(Update dto, Long userId) {
        validate(dto, userId);
        User user = userGetService.find(userId);
        userUpdateService.update(user, dto, passwordEncoder);
    }

    @Override
    @Transactional
    public void apply(SignUp dto) {
        validate(dto);

        Cardinal cardinal = cardinalGetService.find(dto.cardinal());
        User user = mapper.from(dto, passwordEncoder);
        UserCardinal userCardinal = new UserCardinal(user, cardinal);

        userSaveService.save(user);
        userCardinalSaveService.save(userCardinal);
    }

    @Override
    @Transactional
    public void socialRegister(Register dto) {
        validate(dto);

        Cardinal cardinal = cardinalGetService.find(dto.cardinal());

        User user = mapper.from(dto);
        UserCardinal userCardinal = new UserCardinal(user, cardinal);

        userSaveService.save(user);
        userCardinalSaveService.save(userCardinal);
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
        UserCardinalDto dto = getUserCardinalDto(userId);

        return mapper.toUserInfoDto(dto.user(), dto.cardinals());
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

    private UserCardinalDto getUserCardinalDto(Long userId) {
        User user = userGetService.find(userId);
        List<UserCardinal> userCardinals = userCardinalGetService.getUserCardinals(user);

        return cardinalMapper.toUserCardinalDto(user, userCardinals);
    }
}
