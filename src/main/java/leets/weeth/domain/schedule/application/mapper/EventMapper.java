package leets.weeth.domain.schedule.application.mapper;

import leets.weeth.domain.schedule.domain.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Response;
import static leets.weeth.domain.schedule.application.dto.EventDTO.Save;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    Response to(Event event);

    @Mapping(target = "id", ignore = true)
    Event from(Save dto);
}
