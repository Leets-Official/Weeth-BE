package leets.weeth.domain.schedule.application.usecase;

import static leets.weeth.domain.schedule.application.dto.EventDTO.*;

public interface EventUseCase {

    Response find(Long eventId);

    void save(Save dto, Long userId);

    void update(Long eventId, Update dto, Long userId);
}
