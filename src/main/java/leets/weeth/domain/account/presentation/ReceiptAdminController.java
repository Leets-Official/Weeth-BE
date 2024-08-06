package leets.weeth.domain.account.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.account.application.dto.ReceiptDTO;
import leets.weeth.domain.account.application.usecase.ReceiptUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/receipts")
public class ReceiptAdminController {

    private final ReceiptUseCase receiptUseCase;

    @PostMapping
    public CommonResponse<Void> save(@RequestPart @Valid ReceiptDTO.Save dto, @RequestPart(required = false) List<MultipartFile> images) {
        receiptUseCase.save(dto, images);
        return CommonResponse.createSuccess();
    }

    @DeleteMapping("/{receiptId}")
    public CommonResponse<Void> delete(@PathVariable Long receiptId) {
        receiptUseCase.delete(receiptId);
        return CommonResponse.createSuccess();
    }
}
