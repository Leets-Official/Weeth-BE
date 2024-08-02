package leets.weeth.domain.schedule.application.dto;

import java.time.LocalDateTime;

public class MeetingDTO {

    public record Response(
            Long id,
            String title,
            String content,
            String location,
            LocalDateTime start,
            LocalDateTime end,
            Integer weekNumber,
            Integer cardinal,
            Integer code,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {}
}
