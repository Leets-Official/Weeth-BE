package leets.weeth.domain.account.application.dto;

import java.time.LocalDate;
import java.util.List;

public class ReceiptDTO {

    public record Response(
        Long id,
        String description,
        Integer price,
        LocalDate date,
        List<String> images
    ) {}
}
