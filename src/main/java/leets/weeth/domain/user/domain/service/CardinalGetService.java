package leets.weeth.domain.user.domain.service;

import leets.weeth.domain.user.application.exception.CardinalNotFoundException;
import leets.weeth.domain.user.domain.entity.Cardinal;
import leets.weeth.domain.user.domain.repository.CardinalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardinalGetService {

    private final CardinalRepository cardinalRepository;

    public Cardinal find(Integer cardinal) {
        return cardinalRepository.findByCardinalNumber(cardinal)
                .orElseThrow(CardinalNotFoundException::new);
    }

    public List<Cardinal> findAll() {
        return cardinalRepository.findAll();
    }

    public void validateCardinal(Integer cardinal) {
        cardinalRepository.findByCardinalNumber(cardinal)
                .orElseThrow(CardinalNotFoundException::new);
    }
}
