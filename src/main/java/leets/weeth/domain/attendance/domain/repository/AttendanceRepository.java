package leets.weeth.domain.attendance.domain.repository;

import leets.weeth.domain.attendance.domain.entity.Attendance;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByMeeting(Meeting meeting);

    @Modifying
    @Query("DELETE FROM Attendance a WHERE a IN :attendances")
    void deleteAll(@Param("attendances") List<Attendance> attendances);
}
