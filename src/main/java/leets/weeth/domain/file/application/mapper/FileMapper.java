package leets.weeth.domain.file.application.mapper;

import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.file.application.dto.response.FileResponse;
import leets.weeth.domain.file.application.dto.response.UrlResponse;
import leets.weeth.domain.file.domain.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper {

    @Mapping(target = "post", source = "post") // post 매핑
    File toFile(String fileName, String fileUrl, Post post);

    @Mapping(target = "notice", source = "notice") // post 매핑
    File toFile(String fileName, String fileUrl, Notice notice);

    FileResponse toFileResponse(File file);

    UrlResponse toUrlResponse(String fileName, String putUrl);
}
