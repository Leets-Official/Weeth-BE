package leets.weeth.domain.account.presentation;

import static leets.weeth.domain.account.presentation.ResponseMessage.ACCOUNT_SAVE_SUCCESS;

import jakarta.validation.Valid;
import leets.weeth.domain.account.application.dto.AccountDTO;
import leets.weeth.domain.account.application.usecase.AccountUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/account")
public class AccountAdminController {

    private final AccountUseCase accountUseCase;

    @PostMapping
    public CommonResponse<Void> save(@RequestBody @Valid AccountDTO.Save dto) {
        accountUseCase.save(dto);
        return CommonResponse.createSuccess(ACCOUNT_SAVE_SUCCESS.getMessage());
    }
}
