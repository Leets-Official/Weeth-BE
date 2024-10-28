package leets.weeth.domain.board.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class PostNotFoundException extends BusinessLogicException {
    public PostNotFoundException() {
        super(404, "존재하지 않는 게시물입니다.");
    }
}