package leets.weeth.domain.account.application.usecase;

import jakarta.transaction.Transactional;
import leets.weeth.domain.account.application.dto.ReceiptDTO;
import leets.weeth.domain.account.application.mapper.ReceiptMapper;
import leets.weeth.domain.account.domain.entity.Account;
import leets.weeth.domain.account.domain.entity.Receipt;
import leets.weeth.domain.account.domain.service.AccountGetService;
import leets.weeth.domain.account.domain.service.ReceiptDeleteService;
import leets.weeth.domain.account.domain.service.ReceiptGetService;
import leets.weeth.domain.account.domain.service.ReceiptSaveService;
import leets.weeth.domain.file.service.FileSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptUseCaseImpl implements ReceiptUseCase {

    private final ReceiptDeleteService receiptDeleteService;
    private final ReceiptSaveService receiptSaveService;
    private final FileSaveService fileSaveService;
    private final ReceiptMapper mapper;
    private final AccountGetService accountGetService;
    private final ReceiptGetService receiptGetService;

    @Override @Transactional
    public void save(ReceiptDTO.Save dto, List<MultipartFile> files) {
        List<String> images = fileSaveService.uploadFiles(files);
        Account account = accountGetService.find(dto.cardinal());
        Receipt receipt = receiptSaveService.save(mapper.from(dto, images, account));
        account.spend(receipt);
    }

    @Override @Transactional
    public void delete(Long id) {
        Receipt receipt = receiptGetService.find(id);
        receipt.getAccount().cancel(receipt);
        receiptDeleteService.delete(receipt);
    }
}
