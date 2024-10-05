package leets.weeth.domain.user.application.exception;

import jakarta.persistence.EntityNotFoundException;

public class CardinalNotFoundException extends EntityNotFoundException {
    public CardinalNotFoundException() {super("존재하지 않는 기수입니다.");}
}
