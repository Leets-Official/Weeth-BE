package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class UserNotMatchException extends BusinessLogicException {
    public UserNotMatchException() {super(400, "생성한 사용자와 일치하지 않습니다.");}
}
