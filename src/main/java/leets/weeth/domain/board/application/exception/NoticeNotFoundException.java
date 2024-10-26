package leets.weeth.domain.board.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class NoticeNotFoundException extends BusinessLogicException {
    public NoticeNotFoundException() {super(400, "존재하지 않는 공지사항입니다.");}
}
