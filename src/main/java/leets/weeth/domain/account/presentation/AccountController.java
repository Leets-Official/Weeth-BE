package leets.weeth.domain.account.presentation;

import leets.weeth.domain.account.application.dto.AccountDTO;
import leets.weeth.domain.account.application.usecase.AccountUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountUseCase accountUseCase;

    @GetMapping("/{cardinal}")
    public CommonResponse<AccountDTO.Response> find(@PathVariable Integer cardinal) {
        return CommonResponse.createSuccess(accountUseCase.find(cardinal));
    }
}
