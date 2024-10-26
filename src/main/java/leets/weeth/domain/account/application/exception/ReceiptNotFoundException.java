package leets.weeth.domain.account.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class ReceiptNotFoundException extends BusinessLogicException {
    public ReceiptNotFoundException() { super(400, "존재하지 않는 내역입니다.");}
}

