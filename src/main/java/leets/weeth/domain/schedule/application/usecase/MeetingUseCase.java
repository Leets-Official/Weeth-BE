package leets.weeth.domain.schedule.application.usecase;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.*;

public interface MeetingUseCase {

    Response find(Long eventId);

    void save(Save dto, Long userId);

    void update(Update dto, Long userId, Long meetingId);

    void delete(Long meetingId);
}
