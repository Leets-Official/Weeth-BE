package leets.weeth.domain.schedule.application.usecase;

import leets.weeth.domain.schedule.application.dto.ScheduleDTO;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Response;

public interface MeetingUseCase {

    Response find(Long eventId);

    void save(ScheduleDTO.Save dto, Long userId);

    void update(ScheduleDTO.Update dto, Long userId, Long meetingId);

    void delete(Long meetingId);
}
