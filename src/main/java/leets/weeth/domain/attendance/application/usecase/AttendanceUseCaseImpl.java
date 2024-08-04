package leets.weeth.domain.attendance.application.usecase;

import leets.weeth.domain.attendance.domain.entity.Attendance;
import leets.weeth.domain.attendance.domain.entity.enums.Status;
import leets.weeth.domain.attendance.domain.service.AttendanceUpdateService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.common.error.exception.custom.AttendanceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttendanceUseCaseImpl implements AttendanceUseCase {

    private final UserGetService userGetService;
    private final AttendanceUpdateService attendanceUpdateService;

    @Override
    public void checkIn(Long userId) {
        User user = userGetService.find(userId);

        LocalDateTime now = LocalDateTime.now();
        Attendance todayMeeting = user.getAttendances().stream()
                .filter(attendance -> attendance.getMeeting().getStart().isBefore(now)
                        && attendance.getMeeting().getEnd().isAfter(now))
                .findAny()
                .orElseThrow(AttendanceNotFoundException::new);

        if (todayMeeting.getStatus() == Status.ATTEND)
            attendanceUpdateService.attend(todayMeeting);
    }
}
