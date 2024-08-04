package leets.weeth.domain.attendance.domain.service;

import jakarta.transaction.Transactional;
import leets.weeth.domain.attendance.domain.entity.Attendance;
import leets.weeth.domain.attendance.domain.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceUpdateService {

    private final AttendanceRepository attendanceRepository;

    public void attend(Attendance attendance) {
        attendance.attend();
        attendance.getUser().attend();
    }
}
