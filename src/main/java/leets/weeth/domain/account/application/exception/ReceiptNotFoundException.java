package leets.weeth.domain.account.application.exception;


import jakarta.persistence.EntityExistsException;

public class ReceiptNotFoundException extends EntityExistsException {
    public ReceiptNotFoundException() { super("존재하지 않는 내역입니다.");}
}

