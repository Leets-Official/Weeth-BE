package leets.weeth.domain.account.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class AccountExistsException extends BusinessLogicException {
    public AccountExistsException() { super(400, "이미 생성된 장부입니다.");}
}

