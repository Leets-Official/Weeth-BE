package leets.weeth.domain.schedule.domain.service;

import leets.weeth.domain.schedule.application.mapper.EventMapper;
import leets.weeth.domain.schedule.domain.entity.Event;
import leets.weeth.domain.schedule.domain.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Update;

@Service
@RequiredArgsConstructor
public class EventUpdateService {

    private final EventRepository eventRepository;
    private final EventMapper mapper;

    public void update(Long eventId, Update dto) {
        Event update = mapper.update(eventId, dto);
        eventRepository.save(update);
    }
}
