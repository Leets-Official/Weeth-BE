package leets.weeth.domain.account.application.usecase;

import leets.weeth.domain.account.application.dto.ReceiptDTO;
import leets.weeth.domain.account.application.mapper.ReceiptMapper;
import leets.weeth.domain.account.domain.service.ReceiptSaveService;
import leets.weeth.domain.file.service.FileSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptUseCaseImpl implements ReceiptUseCase {

    private ReceiptSaveService receiptSaveService;
    private FileSaveService fileSaveService;
    private ReceiptMapper mapper;

    @Override
    public void save(ReceiptDTO.Save dto, List<MultipartFile> files) {
        List<String> images = fileSaveService.uploadFiles(files);
        receiptSaveService.save(mapper.from(dto, images));
    }
}
