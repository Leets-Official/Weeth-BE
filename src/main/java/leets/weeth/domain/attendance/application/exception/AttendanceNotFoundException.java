package leets.weeth.domain.attendance.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class AttendanceNotFoundException extends BusinessLogicException {
    public AttendanceNotFoundException() {super(404, "출석 정보가 존재하지 않습니다.");}
}