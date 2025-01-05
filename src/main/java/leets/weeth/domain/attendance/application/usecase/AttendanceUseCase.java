package leets.weeth.domain.attendance.application.usecase;

import leets.weeth.domain.attendance.application.exception.AttendanceCodeMismatchException;

import java.time.LocalDate;

import static leets.weeth.domain.attendance.application.dto.AttendanceDTO.Detail;
import static leets.weeth.domain.attendance.application.dto.AttendanceDTO.Main;

public interface AttendanceUseCase {
    void checkIn(Long userId, Integer code) throws AttendanceCodeMismatchException;

    Main find(Long userId);

    Detail findAll(Long userId);

    void close(LocalDate now, Integer cardinal);

    void updateMeetingStatus(Long meetingId);
}
