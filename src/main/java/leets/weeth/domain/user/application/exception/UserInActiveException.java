package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class UserInActiveException extends BusinessLogicException {
    public UserInActiveException() {
        super(403, "가입 승인이 허가되지 않은 계정입니다.");
    }
}
