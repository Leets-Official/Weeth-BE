package leets.weeth.domain.attendance.domain.service;

import java.util.List;
import leets.weeth.domain.attendance.domain.entity.Attendance;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.schedule.domain.entity.enums.MeetingStatus;
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

    @Scheduled(cron = "0 0 22 * * THU", zone = "Asia/Seoul")
    public void autoCloseAttendance() {
        Cardinal currentCardinal = cardinalRepository.findTopByOrderByCardinalNumberDesc();

        List<Meeting> meetings = meetingRepository.findAllByCardinalOrderByStartAsc(currentCardinal.getCardinalNumber());

        meetings.stream()
                .filter(meeting -> meeting.getMeetingStatus() == MeetingStatus.OPEN)
                .forEach(meeting -> {
                    meeting.close();
                    List<Attendance> attendanceList = attendanceGetService.findAllByMeeting(meeting);
                    attendanceUpdateService.close(attendanceList);
                });
    }
}
