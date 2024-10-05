package leets.weeth.domain.attendance.application.exception;
import jakarta.persistence.EntityNotFoundException;

public class AttendanceNotFoundException extends EntityNotFoundException {
    public AttendanceNotFoundException() {super("출석 정보가 존재하지 않습니다.");}
}