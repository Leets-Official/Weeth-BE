package leets.weeth.domain.board.application.mapper;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoticeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Notice fromNoticeDto(NoticeDTO.Save dto, List<String> fileUrls, User user);

//    @Mapping(target = "id", source = "noticeId")
//    @Mapping(target = "user", source = "user")
//    Notice update(Long noticeId, NoticeDTO.Update dto, List<String> fileUrls, User user);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "time", source = "modifiedAt")
    NoticeDTO.Response toNoticeDto(Notice notice);

}
