package leets.weeth.domain.schedule.application.usecase;

import leets.weeth.domain.schedule.application.dto.ScheduleDTO;
import leets.weeth.domain.schedule.application.mapper.EventMapper;
import leets.weeth.domain.schedule.domain.entity.Event;
import leets.weeth.domain.schedule.domain.service.EventDeleteService;
import leets.weeth.domain.schedule.domain.service.EventGetService;
import leets.weeth.domain.schedule.domain.service.EventSaveService;
import leets.weeth.domain.schedule.domain.service.EventUpdateService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.CardinalGetService;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Response;

@Service
@RequiredArgsConstructor
public class EventUseCaseImpl implements EventUseCase {

    private final UserGetService userGetService;
    private final EventGetService eventGetService;
    private final EventSaveService eventSaveService;
    private final EventUpdateService eventUpdateService;
    private final EventDeleteService eventDeleteService;
    private final CardinalGetService cardinalGetService;
    private final EventMapper mapper;

    @Override
    public Response find(Long eventId) {
        return mapper.to(eventGetService.find(eventId));
    }

    @Override
    @Transactional
    public void save(ScheduleDTO.Save dto, Long userId) {
        User user = userGetService.find(userId);
        cardinalGetService.findByUserSide(dto.cardinal());

        eventSaveService.save(mapper.from(dto, user));
    }

    @Override
    @Transactional
    public void update(Long eventId, ScheduleDTO.Update dto, Long userId) {
        User user = userGetService.find(userId);
        Event event = eventGetService.find(eventId);
        eventUpdateService.update(event, dto, user);
    }

    @Override
    @Transactional
    public void delete(Long eventId) {
        Event event = eventGetService.find(eventId);
        eventDeleteService.delete(event);
    }
}
