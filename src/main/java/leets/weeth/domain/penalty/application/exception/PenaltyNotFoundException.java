package leets.weeth.domain.penalty.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class PenaltyNotFoundException extends BusinessLogicException {
    public PenaltyNotFoundException() {
        super(404, "존재하지 않는 패널티입니다.");
    }
}