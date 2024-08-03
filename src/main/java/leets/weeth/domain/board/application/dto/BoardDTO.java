package leets.weeth.domain.board.application.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BoardDTO {

    public record Response(
            Long id,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            List<String> fileUrls,
            Integer commentCount
    ){}
}
