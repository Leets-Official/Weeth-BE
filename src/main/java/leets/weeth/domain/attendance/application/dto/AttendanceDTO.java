package leets.weeth.domain.attendance.application.dto;

import leets.weeth.domain.attendance.domain.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class AttendanceDTO {

    public record Main(
            Integer attendanceRate,
            String title,
            LocalDateTime start,
            LocalDateTime end,
            String location
    ) {}

    public record Detail(
            Integer attendanceCount,
            Integer total,
            Integer absenceCount,
            List<Response> attendances
    ) {}

    public record Response(
            Long id,
            Status status,
            Integer weekNumber,

            String title,
            LocalDateTime start,
            LocalDateTime end,
            String location
    ) {}

    public record CheckIn(
            Integer code
    ) {}
}
