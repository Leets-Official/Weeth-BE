package leets.weeth.domain.schedule.application.mapper;

import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.*;

import java.util.Random;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeetingMapper {

    @Mapping(target = "memberCount", expression = "java( getMemberCount(meeting) )")
    @Mapping(target = "name", source = "user.name")
    Response to(Meeting meeting);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "code", expression = "java( generateCode() )"),
            @Mapping(target = "user", source = "user")
    })
    Meeting from(Save dto, User user);

    @Mapping(target = "user", source = "user")
    Meeting update(Long id, Update dto, User user);

    default Integer generateCode() {
        return new Random().nextInt(9000) + 1000;
    }

    default Integer getMemberCount(Meeting meeting) {
        return meeting.getAttendances().size();
    }
}
