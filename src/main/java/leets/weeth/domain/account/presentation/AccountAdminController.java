package leets.weeth.domain.account.presentation;

import static leets.weeth.domain.account.presentation.ResponseMessage.ACCOUNT_SAVE_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.weeth.domain.account.application.dto.AccountDTO;
import leets.weeth.domain.account.application.usecase.AccountUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AdminController", description = "어드민 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/account")
public class AccountAdminController {

    private final AccountUseCase accountUseCase;

    @PostMapping
    @Operation(summary="회비 총 금액 기입")
    public CommonResponse<Void> save(@RequestBody @Valid AccountDTO.Save dto) {
        accountUseCase.save(dto);
        return CommonResponse.createSuccess(ACCOUNT_SAVE_SUCCESS.getMessage());
    }
}
