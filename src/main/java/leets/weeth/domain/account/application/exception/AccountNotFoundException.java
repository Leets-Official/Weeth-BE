package leets.weeth.domain.account.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class AccountNotFoundException extends BusinessLogicException {
    public AccountNotFoundException() {super(404, "존재하지 않는 장부입니다.");}
}
