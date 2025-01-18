package leets.weeth.domain.board.application.mapper;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.domain.entity.Post;
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
public interface PostMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", source = "user")
    })
    Post fromPostDto(PostDTO.Save dto, User user);

    @Mappings({
            @Mapping(target = "name", source = "post.user.name"),
            @Mapping(target = "position", source = "post.user.position"),
            @Mapping(target = "role", source = "post.user.role"),
            @Mapping(target = "time", source = "post.modifiedAt"),
            @Mapping(target = "hasFile", expression = "java(fileExists)")
    })
    PostDTO.ResponseAll toAll(Post post, boolean fileExists);


    @Mappings({
            @Mapping(target = "name", source = "post.user.name"),
            @Mapping(target = "position", source = "post.user.position"),
            @Mapping(target = "role", source = "post.user.role"),
            @Mapping(target = "time", source = "post.modifiedAt")
    })
    PostDTO.Response toPostDto(Post post, List<FileResponse> fileUrls, List<CommentDTO.Response> comments);

}
