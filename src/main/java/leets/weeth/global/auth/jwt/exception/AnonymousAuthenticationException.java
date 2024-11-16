package leets.weeth.global.auth.jwt.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class AnonymousAuthenticationException extends BusinessLogicException {
    public AnonymousAuthenticationException() {
        super(401, "인증정보가 존재하지 않습니다.");
    }
}
