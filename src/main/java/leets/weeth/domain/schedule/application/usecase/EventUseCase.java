package leets.weeth.domain.schedule.application.usecase;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Response;
import static leets.weeth.domain.schedule.application.dto.EventDTO.Save;

public interface EventUseCase {

    Response find(Long eventId);

    void save(Save dto);
}
