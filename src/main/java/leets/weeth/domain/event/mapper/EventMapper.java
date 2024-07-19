package leets.weeth.domain.event.mapper;

import leets.weeth.domain.event.dto.ResponseEvent;
import leets.weeth.domain.event.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

//    Event fromDto(RequestEvent requestEvent);

    @Mappings({
            @Mapping(source = "user.name", target = "userName"),
            @Mapping(source = "createdAt", target = "created_at"),
            @Mapping(source = "modifiedAt", target = "modified_at"),
    })
    ResponseEvent toDto(Event event);

}