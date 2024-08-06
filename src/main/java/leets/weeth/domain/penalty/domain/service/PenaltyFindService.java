package leets.weeth.domain.penalty.domain.service;

import leets.weeth.domain.penalty.domain.entity.Penalty;
import leets.weeth.domain.penalty.domain.repository.PenaltyRepository;
import leets.weeth.global.common.error.exception.custom.PenaltyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PenaltyFindService {

    private final PenaltyRepository penaltyRepository;

    public Penalty find(Long penaltyId){
        return penaltyRepository.findById(penaltyId)
                .orElseThrow(PenaltyNotFoundException::new);
    }

    public List<Penalty> findAll(Long userId){
        return penaltyRepository.findByUserId(userId);
    }

    public List<Penalty> findAll(){
        return penaltyRepository.findAll();
    }

}
