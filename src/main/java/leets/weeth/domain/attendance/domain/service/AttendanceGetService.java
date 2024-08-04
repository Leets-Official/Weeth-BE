package leets.weeth.domain.attendance.domain.service;

import leets.weeth.domain.attendance.domain.entity.Attendance;
import leets.weeth.domain.attendance.domain.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceGetService {

    private final AttendanceRepository attendanceRepository;

    public Attendance find() {
        return null;
    }
}
