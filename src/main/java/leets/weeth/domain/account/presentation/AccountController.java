package leets.weeth.domain.account.presentation;

import static leets.weeth.domain.account.presentation.ResponseMessage.ACCOUNT_FIND_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.account.application.dto.AccountDTO;
import leets.weeth.domain.account.application.usecase.AccountUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "AccountController", description = "회비 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountUseCase accountUseCase;

    @GetMapping("/{cardinal}")
    @Operation(summary="회비 내역 조회")
    public CommonResponse<AccountDTO.Response> find(@PathVariable Integer cardinal) {
        return CommonResponse.createSuccess(ACCOUNT_FIND_SUCCESS.getMessage(),accountUseCase.find(cardinal));
    }
}
