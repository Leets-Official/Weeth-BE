package leets.weeth.domain.schedule.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class EventNotFoundException extends BusinessLogicException {
    public EventNotFoundException() {
        super(404, "존재하지 않는 일정입니다.");
    }
}