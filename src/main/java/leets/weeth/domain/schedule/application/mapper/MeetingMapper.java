package leets.weeth.domain.schedule.application.mapper;

import leets.weeth.domain.schedule.application.dto.MeetingDTO;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeetingMapper {

    MeetingDTO.Response to(Meeting meeting);

}
