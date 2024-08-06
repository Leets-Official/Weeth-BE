package leets.weeth.domain.account.application.dto;

import jakarta.validation.constraints.NotNull;

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

    public record Save(
            String description,
            @NotNull Integer price,
            @NotNull LocalDate date,
            @NotNull Integer cardinal
    ) {}
}
