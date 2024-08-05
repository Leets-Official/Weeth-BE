package leets.weeth.domain.attendance.application.usecase;

import leets.weeth.domain.attendance.application.dto.AttendanceDTO;
import leets.weeth.domain.attendance.application.mapper.AttendanceMapper;
import leets.weeth.domain.attendance.domain.entity.Attendance;
import leets.weeth.domain.attendance.domain.entity.enums.Status;
import leets.weeth.domain.attendance.domain.service.AttendanceUpdateService;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.schedule.domain.service.MeetingGetService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.common.error.exception.custom.AttendanceCodeMismatchException;
import leets.weeth.global.common.error.exception.custom.AttendanceNotFoundException;
import leets.weeth.global.common.error.exception.custom.MeetingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceUseCaseImpl implements AttendanceUseCase {

    private final UserGetService userGetService;
    private final AttendanceUpdateService attendanceUpdateService;
    private final AttendanceMapper mapper;
    private final MeetingGetService meetingGetService;

    @Override
    public void checkIn(Long userId, Integer code) throws AttendanceCodeMismatchException {
        User user = userGetService.find(userId);

        LocalDateTime now = LocalDateTime.now();
        Attendance todayMeeting = user.getAttendances().stream()
                .filter(attendance -> attendance.getMeeting().getStart().isBefore(now)
                        && attendance.getMeeting().getEnd().isAfter(now))
                .findAny()
                .orElseThrow(AttendanceNotFoundException::new);

        if(todayMeeting.isWrong(code))
            throw new AttendanceCodeMismatchException();

        if (todayMeeting.getStatus() == Status.ATTEND)
            attendanceUpdateService.attend(todayMeeting);
    }

    @Override
    public AttendanceDTO.Main find(Long userId) {
        User user = userGetService.find(userId);

        Attendance todayMeeting = user.getAttendances().stream()
                .filter(attendance -> attendance.getMeeting().getStart().toLocalDate().isEqual(LocalDate.now())
                        && attendance.getMeeting().getEnd().toLocalDate().isEqual(LocalDate.now()))
                .findAny()
                .orElse(null);

        return mapper.toMainDto(todayMeeting);
    }

    @Override
    public AttendanceDTO.Detail findAll(Long userId) {
        User user = userGetService.find(userId);

        List<AttendanceDTO.Response> responses = user.getAttendances().stream()
                .map(mapper::toResponseDto)
                .toList();

        return mapper.toDetailDto(user, responses);
    }

    @Override
    public void close(LocalDate now, Integer cardinal) {
        List<Meeting> meetings = meetingGetService.find(cardinal);

        List<Attendance> attendances = meetings.stream()
                .filter(meeting -> meeting.getStart().toLocalDate().isEqual(now)
                        && meeting.getEnd().toLocalDate().isEqual(now))
                .findAny()
                .map(Meeting::getAttendances)
                .orElseThrow(MeetingNotFoundException::new);

        attendanceUpdateService.close(attendances);
    }
}
