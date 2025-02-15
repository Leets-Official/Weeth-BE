package leets.weeth.domain.schedule.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class MeetingDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Response(
            Long id,
            String title,
            String content,
            String location,
            String requiredItem,
            String name,
            Integer code,
            LocalDateTime start,
            LocalDateTime end,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {}

    public record Info(
            Long id,
            Integer cardinal,
            String title,
            LocalDateTime start
    ) {}

}
