package leets.weeth.domain.file.application.mapper;

import leets.weeth.domain.account.domain.entity.Receipt;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.file.application.dto.request.FileSaveRequest;
import leets.weeth.domain.file.application.dto.response.FileResponse;
import leets.weeth.domain.file.application.dto.response.UrlResponse;
import leets.weeth.domain.file.domain.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CommentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", source = "post")
    File toFileWithPost(String fileName, String fileUrl, Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notice", source = "notice")
    File toFileWithNotice(String fileName, String fileUrl, Notice notice);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receipt", source = "receipt")
    File toFileWithReceipt(String fileName, String fileUrl, Receipt receipt);

    @Mapping(target = "fileId", source = "file.id")
    FileResponse toFileResponse(File file);

    UrlResponse toUrlResponse(String fileName, String putUrl);

    default List<File> toFileList(List<FileSaveRequest> requests, Post post) {
        List<FileSaveRequest> dto = requests;
        if (dto == null || dto.isEmpty()) {
            return Collections.emptyList();
        }

        return dto.stream()
                .map(request -> toFileWithPost(request.fileName(), request.fileUrl(), post))
                .collect(Collectors.toList());
    }

    default List<File> toFileList(List<FileSaveRequest> requests, Notice notice) {
        if (requests == null || requests.isEmpty()) {
            return Collections.emptyList();
        }

        return requests.stream()
                .map(request -> toFileWithNotice(request.fileName(), request.fileUrl(), notice))
                .collect(Collectors.toList());
    }

    default List<File> toFileList(List<FileSaveRequest> requests, Receipt receipt) {
        if (requests == null || requests.isEmpty()) {
            return Collections.emptyList();
        }

        return requests.stream()
                .map(request -> toFileWithReceipt(request.fileName(), request.fileUrl(), receipt))
                .collect(Collectors.toList());
    }
}
