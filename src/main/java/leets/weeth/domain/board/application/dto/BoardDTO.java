package leets.weeth.domain.board.application.dto;

import leets.weeth.domain.comment.application.dto.CommentDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class BoardDTO {

    @Builder
    public record Response(
            Long id,
            String name,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            List<String> fileUrls,
            List<CommentDTO.Response> comments,
            Integer commentCount
    ){}
}
