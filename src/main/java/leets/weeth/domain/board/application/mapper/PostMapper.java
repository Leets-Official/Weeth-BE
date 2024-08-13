package leets.weeth.domain.board.application.mapper;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.comment.domain.entity.Comment;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", source = "user")
    })
    Post fromPostDto(PostDTO.Save dto, List<String> fileUrls, User user);

    @Mappings({
            @Mapping(target = "name", source = "user.name"),
            @Mapping(target = "time", source = "modifiedAt")
    })
    PostDTO.ResponseAll toAll(Post post);

    @Mappings({
            @Mapping(target = "name", source = "user.name"),
            @Mapping(target = "comments", expression = "java(filterParentComments(post.getComments()))"),
            @Mapping(target = "time", source = "modifiedAt")
    })
    PostDTO.Response toPostDto(Post post);

    default List<CommentDTO.Response> filterParentComments(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }

        // 부모 댓글만 필터링하고, 각 부모 댓글에 대해 자식 댓글을 매핑
        return comments.stream()
                .filter(comment -> comment.getParent() == null) // 부모 댓글만 필터링
                .map(this::mapCommentWithChildren) // 자식 댓글 포함하여 매핑
                .collect(Collectors.toList());
    }

    default CommentDTO.Response mapCommentWithChildren(Comment comment) {
        if (comment == null) {
            return null;
        }

        // 기본 댓글 정보 매핑
        CommentDTO.Response.ResponseBuilder response = CommentDTO.Response.builder();

        response.name(comment.getUser().getName());
        response.time(comment.getModifiedAt());
        response.id(comment.getId());
        response.content(comment.getContent());

        // 자식 댓글들을 재귀적으로 매핑하여 children 필드에 설정
        List<CommentDTO.Response> childrenResponses = comment.getChildren().stream()
                .map(this::mapCommentWithChildren) // 자식 댓글도 동일하게 처리
                .collect(Collectors.toList());
        response.children(childrenResponses);

        return response.build();
    }

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "time", source = "modifiedAt")
    CommentDTO.Response mapComment(Comment comment);

}
