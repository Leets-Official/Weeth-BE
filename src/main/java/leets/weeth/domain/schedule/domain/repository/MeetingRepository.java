package leets.weeth.domain.schedule.domain.repository;

import leets.weeth.domain.schedule.domain.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findByStartLessThanEqualAndEndGreaterThanEqualOrderByStartAsc(LocalDateTime start, LocalDateTime end);

    List<Meeting> findAllByCardinalOrderByStartAsc(int cardinal);

    List<Meeting> findAllByCardinalOrderByStartDesc(int cardinal);

    List<Meeting> findAllByCardinal(int cardinal);

    List<Meeting> findAllByOrderByStartDesc();
}
