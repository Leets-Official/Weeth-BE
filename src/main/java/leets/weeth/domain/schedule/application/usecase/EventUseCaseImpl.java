package leets.weeth.domain.schedule.application.usecase;

import leets.weeth.domain.schedule.application.mapper.EventMapper;
import leets.weeth.domain.schedule.domain.service.EventGetService;
import leets.weeth.domain.schedule.domain.service.EventSaveService;
import leets.weeth.domain.schedule.domain.service.EventUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.schedule.application.dto.EventDTO.*;

@Service
@RequiredArgsConstructor
public class EventUseCaseImpl implements EventUseCase {

    private final EventGetService eventGetService;
    private final EventSaveService eventSaveService;
    private final EventUpdateService eventUpdateService;
    private final EventMapper mapper;

    @Override
    public Response find(Long eventId) {
        return mapper.to(eventGetService.find(eventId));
    }

    @Override
    public void save(Save dto) {
        eventSaveService.save(mapper.from(dto));
    }

    @Override
    public void update(Long eventId, Update dto) {
        eventUpdateService.update(eventId, dto);
    }

}
