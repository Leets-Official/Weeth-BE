package leets.weeth.domain.account.application.dto;

import java.util.List;

public class AccountDTO {

    public record Response(
            Long accountId,
            String description,
            Integer totalAmount,
            Integer currentAmount,
            Integer cardinal,
            List<ReceiptDTO.Response> receipts
    ) {}

    public record Save(
            String description,
            Integer totalAmount,
            Integer cardinal
    ) {}
}
