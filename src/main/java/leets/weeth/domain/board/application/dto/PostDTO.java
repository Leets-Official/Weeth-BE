package leets.weeth.domain.board.application.dto;

import jakarta.validation.constraints.NotNull;

public class PostDTO {

    public record Save(
            @NotNull String title,
            @NotNull String content
    ){}
}
