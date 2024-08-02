package leets.weeth.domain.schedule.application.dto;

import java.time.LocalDateTime;

public class ScheduleDTO {

    public record Response(
            Long id,
            String title,
            LocalDateTime start,
            LocalDateTime end,
            Boolean isMeeting
    ) {}
}
