package leets.weeth.domain.schedule.application.usecase;

import leets.weeth.domain.schedule.application.dto.MeetingDTO;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Response;

public interface MeetingUseCase {

    Response find(Long eventId);

    void save(MeetingDTO.Save dto, Long userId);
}
