package leets.weeth.domain.penalty.application.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class PenaltyDTO {

    @Builder
    public record Save(
        Long userId,
        String penaltyDescription
    ){}

    @Builder
    public record Update(
            Long penaltyId,
            String penaltyDescription
    ){}

    @Builder
    public record Response(
        Long userId,
        Integer penaltyCount,
        String name,
        List<Integer> cardinals,
        List<PenaltyDTO.Penalties> Penalties
    ){}

    @Builder
    public record Penalties(
       Long penaltyId,
       String penaltyDescription,
       LocalDateTime time
    ){}

}

