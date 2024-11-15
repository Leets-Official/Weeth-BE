package leets.weeth.domain.file.domain.service;

import leets.weeth.domain.file.domain.entity.File;
import leets.weeth.domain.file.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileSaveService {
    private final FileRepository fileRepository;

    public void Save(File file) {
        fileRepository.save(file);
    }
}
