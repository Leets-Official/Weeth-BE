package leets.weeth.domain.schedule.application.usecase;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Response;

public interface MeetingUseCase {

    Response find(Long eventId);

}
