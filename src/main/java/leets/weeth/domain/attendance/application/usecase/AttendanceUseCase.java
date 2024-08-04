package leets.weeth.domain.attendance.application.usecase;

import leets.weeth.domain.attendance.application.dto.AttendanceDTO;

import static leets.weeth.domain.attendance.application.dto.AttendanceDTO.*;

public interface AttendanceUseCase {
    void checkIn(Long userId);

    Main find(Long userId);

    Detail findAll(Long userId);
}
