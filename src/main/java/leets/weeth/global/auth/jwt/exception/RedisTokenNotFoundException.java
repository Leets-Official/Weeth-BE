package leets.weeth.global.auth.jwt.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class RedisTokenNotFoundException extends BusinessLogicException {
    public RedisTokenNotFoundException() {
        super(404, "저장된 리프레시 토큰이 존재하지 않습니다.");
    }
}
