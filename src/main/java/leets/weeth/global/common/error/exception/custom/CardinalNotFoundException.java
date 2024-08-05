package leets.weeth.global.common.error.exception.custom;

import jakarta.persistence.EntityNotFoundException;

public class CardinalNotFoundException extends EntityNotFoundException {
    public CardinalNotFoundException() {super("존재하지 않는 기수입니다.");}
}
