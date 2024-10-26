package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class DepartmentNotFoundException extends BusinessLogicException {
    public DepartmentNotFoundException() {super(404, "존재하지 않는 학과입니다.");}
}
