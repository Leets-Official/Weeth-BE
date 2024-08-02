package leets.weeth.domain.board.application.mapper;

import leets.weeth.domain.board.application.dto.BoardDTO;
import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoticeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Notice from(NoticeDTO.Save dto, User user);

    @Mapping(target = "id", source = "noticeId")
    @Mapping(target = "user", source = "user")
    Notice update(Long noticeId, NoticeDTO.Update dto, User user);


    BoardDTO.Response to(Notice notice);

}
