package leets.weeth.domain.schedule.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import leets.weeth.domain.schedule.application.annotation.ScheduleTimeCheck;

import java.time.LocalDateTime;

public class EventDTO {

    public record Response(
            Long id,
            String title,
            String content,
            String location,
            String requiredItem,
            String name,
            String memberCount,
            LocalDateTime start,
            LocalDateTime end,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {}

    public record Save(
            @NotBlank String title,
            @NotBlank String content,
            @NotBlank String location,
            @NotBlank String requiredItem,
            @NotNull String memberCount,
            @ScheduleTimeCheck ScheduleDTO.Time time
    ) {}

    public record Update(
            @NotBlank String title,
            @NotBlank String content,
            @NotBlank String location,
            @NotBlank String requiredItem,
            @NotNull String memberCount,
            @ScheduleTimeCheck ScheduleDTO.Time time
    ) {}
}
