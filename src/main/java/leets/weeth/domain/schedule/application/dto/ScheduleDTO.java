package leets.weeth.domain.schedule.application.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ScheduleDTO {

    public record Response(
            Long id,
            String title,
            LocalDateTime start,
            LocalDateTime end,
            Boolean isMeeting
    ) {}

    public record Time(
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {}
}
