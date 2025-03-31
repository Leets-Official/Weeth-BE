package leets.weeth.domain.file.domain.service;

import leets.weeth.domain.file.application.dto.request.FileUpdateRequest;
import leets.weeth.domain.file.domain.entity.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileUpdateService {

    public void update(File file, FileUpdateRequest dto) {
        file.update(dto.fileName(), dto.fileUrl());
    }

}
