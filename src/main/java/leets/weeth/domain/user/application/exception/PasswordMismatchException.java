package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class PasswordMismatchException extends BusinessLogicException {
    public PasswordMismatchException() {
        super(400, "비밀번호가 일치하지 않습니다.");
    }
}
