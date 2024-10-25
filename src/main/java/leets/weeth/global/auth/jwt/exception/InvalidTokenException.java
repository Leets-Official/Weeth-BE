package leets.weeth.global.auth.jwt.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class InvalidTokenException extends BusinessLogicException {
    public InvalidTokenException() {
        super(400, "올바르지 않은 Refresh Token 입니다.");
    }
}
