package leets.weeth.domain.comment.application.mapper;

import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.domain.entity.Comment;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "content", source = "dto.content"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "post", source = "post"),
            @Mapping(target = "parent", source = "parent"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "notice", ignore = true),
            @Mapping(target = "isDeleted", expression =  "java(false)")
    })
    Comment from(CommentDTO.Save dto, Post post, User user, Comment parent);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "content", source = "dto.content"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "notice", source = "notice"),
            @Mapping(target = "parent", source = "parent"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "post", ignore = true),
            @Mapping(target = "isDeleted", expression =  "java(false)")
    })
    Comment from(CommentDTO.Save dto, Notice notice, User user, Comment parent);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "time", source = "modifiedAt")
    CommentDTO.Response to(Comment comment);

//    @Mapping(target = "id", source = "commentId")
//    @Mapping(target = "user", source = "user")
//    Comment update(Long commentId, CommentDTO.Update dto, User user);

}
