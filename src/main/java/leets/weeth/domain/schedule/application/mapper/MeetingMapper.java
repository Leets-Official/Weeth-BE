package leets.weeth.domain.schedule.application.mapper;

import leets.weeth.domain.schedule.application.dto.MeetingDTO;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.*;

import java.util.Random;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeetingMapper {

    MeetingDTO.Response to(Meeting meeting);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "code", expression = "java( generateCode() )"),
            @Mapping(target = "user", source = "user")
    })
    Meeting from(MeetingDTO.Save dto, User user);

    default Integer generateCode() {
        return new Random().nextInt(9000) + 1000;
    }
}
