package leets.weeth.domain.attendance.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class AttendanceEventTypeNotMatchException extends BusinessLogicException {
    public AttendanceEventTypeNotMatchException() {super(400, "출석일정은 직접 수정할 수 없습니다.");}
}
