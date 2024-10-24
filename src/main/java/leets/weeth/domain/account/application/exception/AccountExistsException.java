package leets.weeth.domain.account.application.exception;


import jakarta.persistence.EntityExistsException;

public class AccountExistsException extends EntityExistsException {
    public AccountExistsException() { super("이미 생성된 장부입니다.");}
}

