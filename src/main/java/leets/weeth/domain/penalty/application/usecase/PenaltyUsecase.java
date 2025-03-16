package leets.weeth.domain.penalty.application.usecase;

import leets.weeth.domain.penalty.application.dto.PenaltyDTO;

import java.util.List;

public interface PenaltyUsecase {

    void save(PenaltyDTO.Save dto);

    void update(PenaltyDTO.Update dto);

    List<PenaltyDTO.Response> find();

    PenaltyDTO.Response find(Long userId);

    void delete(Long penaltyId);

}
