package leets.weeth.domain.attendance.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class AttendanceCodeMismatchException extends BusinessLogicException {
    public AttendanceCodeMismatchException() {super(400 ,"출석 코드가 일치하지 않습니다.");}
}