package leets.weeth.domain.user.application.dto.response;

import java.time.LocalDateTime;

public record CardinalResponse(
        Long id,
        Integer cardinalNumber,
        Integer year,
        Integer semester,
        LocalDateTime createdAt,
        LocalDateTime modified_at
) {
}
