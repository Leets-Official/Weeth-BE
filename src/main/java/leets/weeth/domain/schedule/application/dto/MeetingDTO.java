package leets.weeth.domain.schedule.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import leets.weeth.domain.schedule.application.annotation.ScheduleTimeCheck;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static leets.weeth.domain.schedule.application.dto.ScheduleDTO.*;

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
            String name,
            Integer memberCount,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {}

    public record Save(
            @NotBlank String title,
            @NotBlank String content,
            @NotBlank String location,
            @ScheduleTimeCheck Time time,
            @NotNull Integer weekNumber,
            @NotNull Integer cardinal
    ) {}

    public record Update(
            @NotBlank String title,
            @NotBlank String content,
            @NotBlank String location,
            @ScheduleTimeCheck Time time,
            @NotNull Integer weekNumber,
            @NotNull Integer cardinal
    ) {}
}
