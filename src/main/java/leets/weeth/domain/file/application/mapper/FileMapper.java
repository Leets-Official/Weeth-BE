package leets.weeth.domain.file.application.mapper;

import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.file.application.dto.response.FileDto;
import leets.weeth.domain.file.application.dto.response.FileResponse;
import leets.weeth.domain.file.domain.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper {

    File toFile(String fileName, String fileUrl);

    FileResponse toFileResponse(File file);

    FileDto toFileDto(String putUrl, String getUrl);
}
