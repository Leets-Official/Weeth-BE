package leets.weeth.domain.board.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class NoticeDTO {

    @Builder
    public record Save(
            @NotNull String title,
            @NotNull String content
    ){}

    @Builder
    public record Update(
            @NotNull String title,
            @NotNull String content
    ){}
}
