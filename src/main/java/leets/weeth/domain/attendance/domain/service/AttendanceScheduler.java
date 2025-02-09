package leets.weeth.domain.attendance.domain.service;

import java.time.LocalDate;
import java.util.List;
import leets.weeth.domain.attendance.domain.entity.Attendance;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.schedule.domain.repository.MeetingRepository;
import leets.weeth.domain.user.domain.entity.Cardinal;
import leets.weeth.domain.user.domain.repository.CardinalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceScheduler {

    private final CardinalRepository cardinalRepository;
    private final MeetingRepository meetingRepository;
    private final AttendanceGetService attendanceGetService;
    private final AttendanceUpdateService attendanceUpdateService;

    @Scheduled(cron = "0 0 22 * * THU")
    public void autoCloseAttendance() {
        LocalDate today = LocalDate.now();
        Cardinal currentCardinal = cardinalRepository.findTopByOrderByCardinalNumberDesc();

        List<Meeting> meetings = meetingRepository.findAllByCardinalOrderByStartAsc(currentCardinal.getCardinalNumber());

        meetings.stream()
                .filter(meeting -> meeting.getStart().toLocalDate().isEqual(today) &&
                        meeting.getEnd().toLocalDate().isEqual(today))
                .findAny()
                .ifPresent(meeting -> {
                    List<Attendance> attendanceList = attendanceGetService.findAllByMeeting(meeting);
                    attendanceUpdateService.close(attendanceList);
                });
    }
}
