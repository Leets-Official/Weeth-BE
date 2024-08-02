package leets.weeth.domain.schedule.application.mapper;

import leets.weeth.domain.schedule.domain.entity.Event;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.*;

import static leets.weeth.domain.schedule.application.dto.EventDTO.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    Response to(Event event);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", source = "user")
    })
    Event from(Save dto, User user);

    @Mapping(target = "user", source = "user")
    Event update(Long id, Update dto, User user);
}
