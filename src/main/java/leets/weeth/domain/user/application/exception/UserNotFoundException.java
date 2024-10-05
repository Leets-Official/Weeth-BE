package leets.weeth.domain.user.application.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super("존재하지 않는 유저입니다.");
    }
}