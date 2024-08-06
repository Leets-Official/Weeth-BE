package leets.weeth.domain.penalty.application.usecase;

import jakarta.transaction.Transactional;
import leets.weeth.domain.penalty.application.dto.PenaltyDTO;
import leets.weeth.domain.penalty.application.mapper.PenaltyMapper;
import leets.weeth.domain.penalty.domain.entity.Penalty;
import leets.weeth.domain.penalty.domain.service.PenaltyDeleteService;
import leets.weeth.domain.penalty.domain.service.PenaltyFindService;
import leets.weeth.domain.penalty.domain.service.PenaltySaveService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PenaltyUsecaseImpl implements PenaltyUsecase{

    private final PenaltySaveService penaltySaveService;
    private final PenaltyFindService penaltyFindService;
    private final PenaltyDeleteService penaltyDeleteService;

    private final UserGetService userGetService;

    private final PenaltyMapper mapper;

    @Override
    @Transactional
    public void save(PenaltyDTO.Save dto) {
        User user = userGetService.find(dto.userId());
        Penalty penalty = mapper.fromPenaltyDto(dto, user);
        penaltySaveService.save(penalty);

        user.addPenalty(penalty);
        user.incrementPenaltyCount();
    }

    @Override
    public List<PenaltyDTO.Response> find() {
        List<Penalty> penalties = penaltyFindService.findAll();

        Map<Long, List<Penalty>> penaltiesByUser = penalties.stream()
                .collect(Collectors.groupingBy(penalty -> penalty.getUser().getId()));

        return penaltiesByUser.entrySet().stream()
                .map(entry -> {
                    Long userId = entry.getKey();
                    User user = userGetService.find(userId);

                    List<PenaltyDTO.Penalties> penaltyDTOs = entry.getValue().stream()
                            .map(mapper::toPenalties)
                            .toList();

                    return mapper.toPenaltyDto(user, penaltyDTOs);
                })
                .sorted(Comparator.comparing(PenaltyDTO.Response::userId))
                .toList();
    }

    @Override
    public PenaltyDTO.Response find(Long userId) {
        User user = userGetService.find(userId);

        List<PenaltyDTO.Penalties> penalties = penaltyFindService.findAll(userId)
                .stream()
                .map(mapper::toPenalties)
                .toList();

        return mapper.toPenaltyDto(user, penalties);
    }

    @Override
    public void delete(Long penaltyId) {
        Penalty penalty = penaltyFindService.find(penaltyId);
        penalty.getUser().decrementPenaltyCount();
        penaltyDeleteService.delete(penaltyId);
    }

}
