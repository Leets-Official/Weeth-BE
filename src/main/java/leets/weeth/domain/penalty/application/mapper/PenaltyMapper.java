package leets.weeth.domain.penalty.application.mapper;

import leets.weeth.domain.penalty.application.dto.PenaltyDTO;
import leets.weeth.domain.penalty.domain.entity.Penalty;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PenaltyMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Penalty fromPenaltyDto(PenaltyDTO.Save dto, User user);


    @Mapping(target = "Penalties", source = "penalties")
    @Mapping(target = "userId", source = "user.id")
    PenaltyDTO.Response toPenaltyDto(User user, List<PenaltyDTO.Penalties> penalties);

    @Mapping(target = "time", source = "modifiedAt")
    @Mapping(target = "penaltyId", source = "id")
    PenaltyDTO.Penalties toPenalties(Penalty penalty);

}
