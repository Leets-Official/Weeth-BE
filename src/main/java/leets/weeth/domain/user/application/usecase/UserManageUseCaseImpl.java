package leets.weeth.domain.user.application.usecase;

import jakarta.transaction.Transactional;
import leets.weeth.domain.attendance.domain.service.AttendanceSaveService;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.schedule.domain.service.MeetingGetService;
import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.enums.Status;
import leets.weeth.domain.user.domain.service.UserDeleteService;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.domain.user.domain.service.UserUpdateService;
import leets.weeth.global.auth.jwt.service.JwtRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static leets.weeth.domain.user.application.dto.response.UserResponseDto.AdminResponse;
import static leets.weeth.domain.user.domain.entity.enums.Status.*;

@Service
@RequiredArgsConstructor
public class UserManageUseCaseImpl implements UserManageUseCase {

    private final UserGetService userGetService;
    private final UserUpdateService userUpdateService;
    private final UserDeleteService userDeleteService;

    private final AttendanceSaveService attendanceSaveService;
    private final MeetingGetService meetingGetService;
    private final JwtRedisService jwtRedisService;

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AdminResponse> findAllByAdmin() {
        // 우선순위 지정
        Map<Status, Integer> statusPriority = Map.of(
                ACTIVE, 1,
                WAITING, 2,
                LEFT, 3,
                BANNED, 4
        );

        return userGetService.findAll().stream()
                .sorted(Comparator.comparingInt(user -> statusPriority.getOrDefault(user.getStatus(), Integer.MAX_VALUE)))
                .map(mapper::toAdminResponse)
                .collect(Collectors.toList());
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

}
