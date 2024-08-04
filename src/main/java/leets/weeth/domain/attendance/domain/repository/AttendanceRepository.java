package leets.weeth.domain.attendance.domain.repository;

import leets.weeth.domain.attendance.domain.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
