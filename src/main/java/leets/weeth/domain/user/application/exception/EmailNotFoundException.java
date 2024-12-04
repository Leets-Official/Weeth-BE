package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class EmailNotFoundException extends BusinessLogicException {
    public EmailNotFoundException() {
        super(404, "Redis에 저장된 email이 없습니다.");
    }
}
