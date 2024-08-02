package leets.weeth.domain.schedule.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    public record Save(
            @NotBlank String title,
            @NotBlank String content,
            @NotBlank String location,
            @NotNull LocalDateTime start,
            @NotNull LocalDateTime end,
            @NotNull Integer weekNumber,
            @NotNull Integer cardinal
    ) {}
}
