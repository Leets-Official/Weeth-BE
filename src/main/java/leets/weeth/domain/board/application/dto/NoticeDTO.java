package leets.weeth.domain.board.application.dto;

import jakarta.validation.constraints.NotNull;

public class NoticeDTO {

    public record Save(
            @NotNull String title,
            @NotNull String content
    ){}

    public record Update(
            @NotNull String title,
            @NotNull String content
    ){}
}
