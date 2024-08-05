package leets.weeth.domain.account.domain.service;

import leets.weeth.domain.account.domain.entity.Receipt;
import leets.weeth.domain.account.domain.repository.ReceiptRepository;
import leets.weeth.global.common.error.exception.custom.ReceiptNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptGetService {

    private final ReceiptRepository receiptRepository;

    public Receipt find(Long id) {
        return receiptRepository.findById(id)
                .orElseThrow(ReceiptNotFoundException::new);
    }
}
