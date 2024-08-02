package leets.weeth.domain.schedule.application.usecase;

import leets.weeth.domain.schedule.application.dto.EventDTO;
import leets.weeth.domain.schedule.application.mapper.EventMapper;
import leets.weeth.domain.schedule.domain.service.EventGetService;
import leets.weeth.domain.schedule.domain.service.EventSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Response;

@Service
@RequiredArgsConstructor
public class EventUseCaseImpl implements EventUseCase {

    private final EventGetService eventGetService;
    private final EventSaveService eventSaveService;
    private final EventMapper mapper;

    @Override
    public Response find(Long eventId) {
        return mapper.to(eventGetService.find(eventId));
    }

    @Override
    public void save(EventDTO.Save dto) {
        eventSaveService.save(mapper.from(dto));
    }

}
