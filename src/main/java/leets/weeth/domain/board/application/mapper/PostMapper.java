package leets.weeth.domain.board.application.mapper;

import leets.weeth.domain.board.application.dto.BoardDTO;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Post from(PostDTO.Save dto, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "user", source = "user")
    Post update(Long postId, PostDTO.Update dto, User user);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "comments", source = "comments")
    BoardDTO.Response to(Post post);
}
