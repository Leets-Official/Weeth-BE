package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class UserExistsException extends BusinessLogicException {
    public UserExistsException() {
        super(400, "이미 가입된 사용자입니다.");
    }
}