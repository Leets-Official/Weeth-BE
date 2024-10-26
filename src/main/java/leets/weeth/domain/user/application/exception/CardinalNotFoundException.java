package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class CardinalNotFoundException extends BusinessLogicException {
    public CardinalNotFoundException() {super(404, "존재하지 않는 기수입니다.");}
}
