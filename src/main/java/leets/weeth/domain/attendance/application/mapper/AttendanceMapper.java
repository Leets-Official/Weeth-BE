package leets.weeth.domain.attendance.application.mapper;

import leets.weeth.domain.attendance.application.dto.AttendanceDTO;
import leets.weeth.domain.attendance.domain.entity.Attendance;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceMapper {

    @Mappings({
            @Mapping(target = "attendanceRate", source = "user.attendanceRate"),
            @Mapping(target = "title", source = "attendance.meeting.title"),
            @Mapping(target = "start", source = "attendance.meeting.start"),
            @Mapping(target = "end", source = "attendance.meeting.end"),
            @Mapping(target = "location", source = "attendance.meeting.location"),
    })
    AttendanceDTO.Main toMainDto(User user, Attendance attendance);

    @Mappings({
            @Mapping(target = "attendances", source = "attendances"),
            @Mapping(target = "total", expression = "java( user.getAttendanceCount() + user.getAbsenceCount() )")
    })
    AttendanceDTO.Detail toDetailDto(User user, List<AttendanceDTO.Response> attendances);

    @Mappings({
            @Mapping(target = "weekNumber", source = "attendance.meeting.weekNumber"),
            @Mapping(target = "title", source = "attendance.meeting.title"),
            @Mapping(target = "start", source = "attendance.meeting.start"),
            @Mapping(target = "end", source = "attendance.meeting.end"),
            @Mapping(target = "location", source = "attendance.meeting.location"),
    })    AttendanceDTO.Response toResponseDto(Attendance attendance);
}
