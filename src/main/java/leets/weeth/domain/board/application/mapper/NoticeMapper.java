package leets.weeth.domain.board.application.mapper;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoticeMapper {

    Notice from(NoticeDTO.Save dto, User user);

//    @Mapping(target = "user", source = "user")
    Notice update(Long noticeId, NoticeDTO.Update dto, User user);
}
