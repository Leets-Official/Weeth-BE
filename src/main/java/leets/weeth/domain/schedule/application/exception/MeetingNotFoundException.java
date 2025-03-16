package leets.weeth.domain.schedule.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class MeetingNotFoundException extends BusinessLogicException {
    public MeetingNotFoundException() {super(404, "존재하지 않는 정기 모임입니다.");}
}
