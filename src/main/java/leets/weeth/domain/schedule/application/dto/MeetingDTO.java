package leets.weeth.domain.schedule.application.dto;

import java.time.LocalDateTime;

public class MeetingDTO {

    public record Response(
            Long id,
            String title,
            String content,
            String location,
            String requiredItem,
            String name,
            LocalDateTime start,
            LocalDateTime end,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {}

}
