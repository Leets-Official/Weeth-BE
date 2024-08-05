package leets.weeth.domain.account.application.usecase;

import leets.weeth.domain.account.application.dto.AccountDTO;
import leets.weeth.domain.account.application.dto.ReceiptDTO;
import leets.weeth.domain.account.application.mapper.AccountMapper;
import leets.weeth.domain.account.application.mapper.ReceiptMapper;
import leets.weeth.domain.account.domain.entity.Account;
import leets.weeth.domain.account.domain.service.AccountGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountUseCaseImpl implements AccountUseCase {

    private final AccountGetService accountGetService;
    private final AccountMapper accountMapper;
    private final ReceiptMapper receiptMapper;

    @Override
    public AccountDTO.Response find(Integer cardinal) {
        Account account = accountGetService.find(cardinal);
        List<ReceiptDTO.Response> receipts = receiptMapper.to(account.getReceipts());
        return accountMapper.to(account, receipts);
    }
}
