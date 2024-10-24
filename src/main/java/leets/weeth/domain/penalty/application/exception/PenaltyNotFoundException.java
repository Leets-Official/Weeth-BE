package leets.weeth.domain.penalty.application.exception;

import jakarta.persistence.EntityNotFoundException;

public class PenaltyNotFoundException extends EntityNotFoundException {
    public PenaltyNotFoundException() {
        super("존재하지 않는 패널티입니다.");
    }
}