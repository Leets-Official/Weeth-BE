package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class RoleNotFoundException extends BusinessLogicException {
    public RoleNotFoundException() {
        super(404, "Redis에 저장된 role이 없습니다.");
    }
}
