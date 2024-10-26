package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class TelExistsException extends BusinessLogicException {
    public TelExistsException() {super(400, "이미 가입된 전화번호 입니다.");}
}
