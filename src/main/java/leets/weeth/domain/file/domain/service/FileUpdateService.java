package leets.weeth.domain.file.domain.service;

import leets.weeth.domain.file.application.dto.request.FileUpdateRequest;
import leets.weeth.domain.file.domain.entity.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUpdateService {

    public void update(File file, FileUpdateRequest dto) {
        file.update(dto.fileName(), dto.fileUrl());
    }

    public void updateFiles(List<File> fileList, List<FileUpdateRequest> dto) {
        for (File file : fileList) {
            dto.stream()
                    .filter(updateRequest -> updateRequest.fileId().equals(file.getId()))
                    .findFirst()
                    .ifPresent(updateRequest -> file.update(updateRequest.fileName(), updateRequest.fileUrl()));
        }
    }
}
