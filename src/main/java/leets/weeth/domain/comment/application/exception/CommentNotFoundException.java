package leets.weeth.domain.comment.application.exception;

import jakarta.persistence.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {
    public CommentNotFoundException() {
        super("존재하지 않는 댓글입니다.");
    }
}
