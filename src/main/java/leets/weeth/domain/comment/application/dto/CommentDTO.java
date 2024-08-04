package leets.weeth.domain.comment.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDTO {

    @Builder
    public record Save(
            Long parentCommentId,
            @NotBlank String content
    ){}

    @Builder
    public record Update(
            @NotBlank String content
    ){}

    @Builder
    public record Response(
            Long userId,
            Long id,
            String name,
            String content,
            List<CommentDTO.Response> children,
            LocalDateTime time //modifiedAt
    ){}

}