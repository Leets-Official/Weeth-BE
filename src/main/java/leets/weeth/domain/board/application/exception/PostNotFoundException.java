package leets.weeth.domain.board.application.exception;

import jakarta.persistence.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException() {
        super("존재하지 않는 게시물입니다.");
    }
}