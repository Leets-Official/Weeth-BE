package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class UserNotFoundException extends BusinessLogicException {
    public UserNotFoundException() {
        super(404, "존재하지 않는 유저입니다.");
    }
}