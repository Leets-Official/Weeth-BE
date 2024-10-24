package leets.weeth.domain.user.application.usecase;

import jakarta.transaction.Transactional;
import leets.weeth.domain.attendance.domain.service.AttendanceSaveService;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.schedule.domain.service.MeetingGetService;
import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserDeleteService;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.domain.user.domain.service.UserSaveService;
import leets.weeth.domain.user.domain.service.UserUpdateService;
import leets.weeth.domain.user.application.exception.StudentIdExistsException;
import leets.weeth.domain.user.application.exception.TelExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static leets.weeth.domain.user.application.dto.UserDTO.*;
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
    public List<AdminResponse> findAllByAdmin() {
        return userGetService.findAll().stream()
                .map(mapper::toAdminResponse)
                .toList();
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

    @Override @Transactional
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
    }

    @Override
    public void leave(Long userId) {
        User user = userGetService.find(userId);
        userDeleteService.leave(user);
    }

    @Override
    public void ban(Long userId) {
        User user = userGetService.find(userId);
        userDeleteService.ban(user);
    }

    @Override @Transactional
    public void applyOB(Long userId, Integer cardinal) {
        User user = userGetService.find(userId);

        if (user.notContains(cardinal)) {
            if(user.isCurrent(cardinal)) {
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
        if(userGetService.validateStudentId(dto.studentId()))
            throw new StudentIdExistsException();
        if(userGetService.validateTel(dto.tel()))
            throw new TelExistsException();
    }

    private void validate(Update dto, Long userId) {
        if(userGetService.validateStudentId(dto.studentId(), userId))
            throw new StudentIdExistsException();
        if(userGetService.validateTel(dto.tel(), userId))
            throw new TelExistsException();
    }
}
