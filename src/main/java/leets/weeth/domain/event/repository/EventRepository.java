package leets.weeth.domain.event.repository;

import leets.weeth.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStartDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

}