package leets.weeth.domain.comment.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class CommentNotFoundException extends BusinessLogicException {
    public CommentNotFoundException() {
        super(404, "존재하지 않는 댓글입니다.");
    }
}
