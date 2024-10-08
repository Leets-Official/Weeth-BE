package leets.weeth.domain.board.application.dto;

import jakarta.validation.constraints.NotNull;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

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

    @Builder
    public record Response(
            Long id,
            String name,
            String title,
            String content,
            LocalDateTime time,//modifiedAt
            Integer commentCount,
            List<String> fileUrls,
            List<CommentDTO.Response> comments
    ){}

    @Builder
    public record ResponseAll(
            Long id,
            String name,
            String title,
            String content,
            LocalDateTime time,//modifiedAt
            Integer commentCount
    ){}

}
