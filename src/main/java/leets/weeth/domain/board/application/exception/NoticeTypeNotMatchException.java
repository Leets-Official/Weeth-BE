package leets.weeth.domain.board.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class NoticeTypeNotMatchException extends BusinessLogicException {
    public NoticeTypeNotMatchException() {super(400, "공지사항은 공지사항 게시판에서 수정하세요.");}
}
