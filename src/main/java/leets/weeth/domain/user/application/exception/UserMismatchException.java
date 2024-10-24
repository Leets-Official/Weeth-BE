package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class UserMismatchException extends BusinessLogicException {
    public UserMismatchException() {
        super(400, "사용자가 현재 사용자와 일치하지 않습니다.");
    }
}