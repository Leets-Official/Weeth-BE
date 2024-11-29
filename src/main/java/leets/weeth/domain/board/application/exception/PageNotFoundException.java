package leets.weeth.domain.board.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class PageNotFoundException extends BusinessLogicException {
    public PageNotFoundException() {
        super(404, "존재하지 않는 페이지입니다.");

    }
}