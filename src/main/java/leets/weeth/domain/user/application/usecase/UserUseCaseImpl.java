package leets.weeth.domain.user.application.usecase;

import jakarta.transaction.Transactional;
import leets.weeth.domain.attendance.domain.service.AttendanceSaveService;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.schedule.domain.service.MeetingGetService;
import leets.weeth.domain.user.application.exception.StudentIdExistsException;
import leets.weeth.domain.user.application.exception.TelExistsException;
import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserDeleteService;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.domain.user.domain.service.UserSaveService;
import leets.weeth.domain.user.domain.service.UserUpdateService;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;
import leets.weeth.global.auth.jwt.application.usecase.JwtManageUseCase;
import leets.weeth.global.auth.jwt.service.JwtRedisService;
import leets.weeth.global.auth.kakao.KakaoAuthService;
import leets.weeth.global.auth.kakao.dto.KakaoTokenResponse;
import leets.weeth.global.auth.kakao.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.*;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.*;
import static leets.weeth.domain.user.domain.entity.enums.LoginStatus.LOGIN;
import static leets.weeth.domain.user.domain.entity.enums.LoginStatus.REGISTER;
import static leets.weeth.domain.user.domain.entity.enums.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserSaveService userSaveService;
    private final UserGetService userGetService;
    private final UserUpdateService userUpdateService;
    private final UserDeleteService userDeleteService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AttendanceSaveService attendanceSaveService;
    private final MeetingGetService meetingGetService;

    private final JwtRedisService jwtRedisService;
    private final JwtManageUseCase jwtManageUseCase;

    private final KakaoAuthService kakaoAuthService;

    @Override
    @Transactional
    public SocialLoginResponse login(login dto) {
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

    private SocialLoginResponse registerUser(String email) {
        User user = User.builder()
                .email(email)
                .build();
        userSaveService.save(user);

        JwtDto dto = jwtManageUseCase.create(user.getId(), email, user.getRole());

        return new SocialLoginResponse(user.getId(), REGISTER, dto.accessToken(), dto.refreshToken());
    }

    private SocialLoginResponse login(String email) {
        User user = userGetService.find(email);

        JwtDto dto = jwtManageUseCase.create(user.getId(), email, user.getRole());

        return new SocialLoginResponse(user.getId(), LOGIN, dto.accessToken(), dto.refreshToken());
    }

    @Override
    public void apply(SignUp dto) {
        validate(dto);
        userSaveService.save(mapper.from(dto, passwordEncoder));
    }

    @Override
    public Map<Integer, List<Response>> findAll() {
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
    public Map<Integer, List<SummaryResponse>> findAllUser() {
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
    public List<AdminResponse> findAllByAdmin() {
        return userGetService.findAll().stream()
                .map(mapper::toAdminResponse)
                .toList();
    }
    @Override
    public UserResponse findUserDetails(Long userId) {
        User user = userGetService.find(userId);
        return mapper.toUserResponse(user);
    }
    @Override
    public Response find(Long userId) {
        return mapper.to(userGetService.find(userId));
    }

    @Override
    public void update(Update dto, Long userId) {
        validate(dto, userId);
        User user = userGetService.find(userId);
        userUpdateService.update(user, dto, passwordEncoder);
    }

    @Override
    @Transactional
    public void accept(Long userId) {
        User user = userGetService.find(userId);

        if (user.isInactive()) {
            userUpdateService.accept(user);
            List<Meeting> meetings = meetingGetService.find(user.getCardinals().get(0));
            attendanceSaveService.save(user, meetings);
        }
    }

    @Override
    public void update(Long userId, String role) {
        User user = userGetService.find(userId);
        userUpdateService.update(user, role);
        jwtRedisService.updateRole(user.getId(), role);
    }

    @Override
    public void leave(Long userId) {
        User user = userGetService.find(userId);
        // 탈퇴하는 경우 리프레시 토큰 삭제
        jwtRedisService.delete(user.getId());
        userDeleteService.leave(user);
    }

    @Override
    public void ban(Long userId) {
        User user = userGetService.find(userId);
        jwtRedisService.delete(user.getId());
        userDeleteService.ban(user);
    }

    @Override
    @Transactional
    public void applyOB(Long userId, Integer cardinal) {
        User user = userGetService.find(userId);

        if (user.notContains(cardinal)) {
            if (user.isCurrent(cardinal)) {
                user.initAttendance();
                List<Meeting> meetings = meetingGetService.find(cardinal);
                attendanceSaveService.save(user, meetings);
            }

            userUpdateService.applyOB(user, cardinal);
        }
    }

    @Override
    public void reset(Long userId) {
        User user = userGetService.find(userId);
        userUpdateService.reset(user, passwordEncoder);
    }

    private void validate(SignUp dto) {
        if (userGetService.validateStudentId(dto.studentId()))
            throw new StudentIdExistsException();
        if (userGetService.validateTel(dto.tel()))
            throw new TelExistsException();
    }

    private void validate(Update dto, Long userId) {
        if (userGetService.validateStudentId(dto.studentId(), userId))
            throw new StudentIdExistsException();
        if (userGetService.validateTel(dto.tel(), userId))
            throw new TelExistsException();
    }
}
