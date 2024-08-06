package leets.weeth.domain.schedule.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class EventDTO {

    public record Response(
            Long id,
            String title,
            String content,
            String location,
            String requiredItem,
            String name,
            Integer memberCount,
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
            @NotNull Integer memberCount,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {}

    public record Update(
            @NotBlank String title,
            @NotBlank String content,
            @NotBlank String location,
            @NotBlank String requiredItem,
            @NotNull Integer memberCount,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {}
}
