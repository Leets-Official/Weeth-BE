package leets.weeth.domain.account.application.usecase;

import leets.weeth.domain.account.application.dto.ReceiptDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReceiptUseCase {
    void save(ReceiptDTO.Save dto);

    void delete(Long id);
}
