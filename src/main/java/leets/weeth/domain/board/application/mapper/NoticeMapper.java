package leets.weeth.domain.board.application.mapper;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.comment.domain.entity.Comment;
import leets.weeth.domain.file.application.dto.response.FileResponse;
import leets.weeth.domain.file.domain.service.FileGetService;
import leets.weeth.domain.user.domain.entity.User;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoticeMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", source = "user")
    })
    Notice fromNoticeDto(NoticeDTO.Save dto, User user);

    @Mappings({
            @Mapping(target = "name", source = "notice.user.name"),
            @Mapping(target = "time", source = "notice.modifiedAt"),
            @Mapping(target = "hasFile", expression = "java(checkHasFile(notice, fileGetService))")
    })
    NoticeDTO.ResponseAll toAll(Notice notice, FileGetService fileGetService);

    default boolean checkHasFile(@MappingTarget Notice notice, FileGetService fileGetService) {
        // 게시글에 파일이 존재하는지 확인
        return fileGetService.checkFileExists(notice);
    }


    @Mappings({
            @Mapping(target = "name", source = "notice.user.name"),
            @Mapping(target = "comments", expression = "java(filterParentComments(notice.getComments()))"),
            @Mapping(target = "time", source = "notice.modifiedAt")
    })
    NoticeDTO.Response toNoticeDto(Notice notice, List<FileResponse> fileUrls);

    default List<CommentDTO.Response> filterParentComments(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }

        // 부모 댓글만 필터링하고, 각 부모 댓글에 대해 자식 댓글을 매핑
        return comments.stream()
                .filter(comment -> comment.getParent() == null) // 부모 댓글만 필터링
                .map(this::mapCommentWithChildren) // 자식 댓글 포함하여 매핑
                .toList();
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

}
