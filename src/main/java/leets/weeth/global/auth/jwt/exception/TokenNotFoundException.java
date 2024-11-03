package leets.weeth.global.auth.jwt.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class TokenNotFoundException extends BusinessLogicException {
    public TokenNotFoundException() {
        super(404, "헤더에서 토큰을 찾을 수 없습니다.");
    }
}
