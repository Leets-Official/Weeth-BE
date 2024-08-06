package leets.weeth.domain.schedule.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

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
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @NotNull Integer weekNumber,
            @NotNull Integer cardinal
    ) {}

    public record Update(
            @NotBlank String title,
            @NotBlank String content,
            @NotBlank String location,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @NotNull Integer weekNumber,
            @NotNull Integer cardinal
    ) {}
}
