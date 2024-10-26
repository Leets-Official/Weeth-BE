package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class StudentIdExistsException extends BusinessLogicException {
    public StudentIdExistsException() {super(400, "이미 가입된 학번입니다.");}
}
