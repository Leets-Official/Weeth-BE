package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class DuplicateCardinalException extends BusinessLogicException {
    public DuplicateCardinalException() {
        super(400, "이미 존재하는 기수 입니다.");
    }
}
