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
import leets.weeth.domain.file.application.mapper.FileMapper;
import leets.weeth.domain.file.domain.entity.File;
import leets.weeth.domain.file.domain.service.FileDeleteService;
import leets.weeth.domain.file.domain.service.FileGetService;
import leets.weeth.domain.file.domain.service.FileSaveService;
import leets.weeth.domain.file.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptUseCaseImpl implements ReceiptUseCase {

    private final ReceiptGetService receiptGetService;
    private final ReceiptDeleteService receiptDeleteService;
    private final ReceiptSaveService receiptSaveService;
    private final AccountGetService accountGetService;

    private final FileGetService fileGetService;
    private final FileSaveService fileSaveService;
    private final FileDeleteService fileDeleteService;

    private final ReceiptMapper mapper;
    private final FileMapper fileMapper;


    @Override
    @Transactional
    public void save(ReceiptDTO.Save dto) {
        Account account = accountGetService.find(dto.cardinal());
        Receipt receipt = receiptSaveService.save(mapper.from(dto, account));
        account.spend(receipt);

        List<File> files = fileMapper.toFileList(dto.files(), receipt);
        fileSaveService.save(files);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Receipt receipt = receiptGetService.find(id);
        List<File> fileList = fileGetService.findAllByReceipt(id);

        receipt.getAccount().cancel(receipt);

        fileDeleteService.delete(fileList);
        receiptDeleteService.delete(receipt);
    }
}
