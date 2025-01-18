package leets.weeth.domain.user.application.usecase;

import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.user.application.dto.request.CardinalSaveRequest;
import leets.weeth.domain.user.application.dto.response.CardinalResponse;
import leets.weeth.domain.user.application.mapper.CardinalMapper;
import leets.weeth.domain.user.domain.entity.Cardinal;
import leets.weeth.domain.user.domain.service.CardinalGetService;
import leets.weeth.domain.user.domain.service.CardinalSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardinalUseCase {

    private final CardinalGetService cardinalGetService;
    private final CardinalSaveService cardinalSaveService;

    private final CardinalMapper cardinalMapper;

    @Transactional
    public void save(CardinalSaveRequest dto) {
        cardinalGetService.validateCardinal(dto.cardinalNumber());

        cardinalSaveService.save(cardinalMapper.from(dto));
    }

    public List<CardinalResponse> findAll() {
        List<Cardinal> cardinals = cardinalGetService.findAll();
        return cardinals.stream()
                .map(cardinalMapper::to)
                .toList();
    }
}
