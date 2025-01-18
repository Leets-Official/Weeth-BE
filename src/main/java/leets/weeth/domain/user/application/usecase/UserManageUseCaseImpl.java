package leets.weeth.domain.user.application.usecase;

import jakarta.transaction.Transactional;
import leets.weeth.domain.attendance.domain.service.AttendanceSaveService;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.schedule.domain.service.MeetingGetService;
import leets.weeth.domain.user.application.exception.InvalidUserOrderException;
import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.entity.Cardinal;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.UserCardinal;
import leets.weeth.domain.user.domain.entity.enums.StatusPriority;
import leets.weeth.domain.user.domain.entity.enums.UsersOrderBy;
import leets.weeth.domain.user.domain.service.*;
import leets.weeth.global.auth.jwt.service.JwtRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import static leets.weeth.domain.user.application.dto.response.UserResponseDto.AdminResponse;
import static leets.weeth.domain.user.domain.entity.enums.UsersOrderBy.NAME_ASCENDING;

@Service
@RequiredArgsConstructor
public class UserManageUseCaseImpl implements UserManageUseCase {

    private final UserGetService userGetService;
    private final UserUpdateService userUpdateService;
    private final UserDeleteService userDeleteService;

    private final AttendanceSaveService attendanceSaveService;
    private final MeetingGetService meetingGetService;
    private final JwtRedisService jwtRedisService;
    private final CardinalGetService cardinalGetService;
    private final UserCardinalSaveService userCardinalSaveService;
    private final UserCardinalGetService userCardinalGetService;

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AdminResponse> findAllByAdmin(UsersOrderBy orderBy) {
        if (orderBy == null || !EnumSet.allOf(UsersOrderBy.class).contains(orderBy)) {
            throw new InvalidUserOrderException();
        }

        if (orderBy.equals(NAME_ASCENDING)) {
            return userGetService.findAll().stream()
                    .sorted(Comparator.comparingInt((user -> (StatusPriority.fromStatus(user.getStatus())).getPriority())))
                    .map(user -> {
                        List<UserCardinal> userCardinals = userCardinalGetService.getUserCardinals(user);
                        return mapper.toAdminResponse(user, userCardinals);
                    })
                    .toList();
        }
        // To do : 추후 기수 분리 후 작업 예정

        return null;
    }

    @Override
    @Transactional
    public void accept(Long userId) {
        User user = userGetService.find(userId);

        Integer cardinal = userCardinalGetService.getCurrentCardinal(user).getCardinalNumber();

        if (user.isInactive()) {
            userUpdateService.accept(user);
            List<Meeting> meetings = meetingGetService.find(cardinal);
            attendanceSaveService.init(user, meetings);
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
        Cardinal nextCardinal = cardinalGetService.find(cardinal);

        if (userCardinalGetService.notContains(user, nextCardinal)) {
            if (userCardinalGetService.isCurrent(user, nextCardinal)) {
                user.initAttendance();
                List<Meeting> meetings = meetingGetService.find(cardinal);
                attendanceSaveService.init(user, meetings);
            }
            UserCardinal userCardinal = new UserCardinal(user, nextCardinal);

            userCardinalSaveService.save(userCardinal);
        }
    }

    @Override
    public void reset(Long userId) {
        User user = userGetService.find(userId);
        userUpdateService.reset(user, passwordEncoder);
    }

}
