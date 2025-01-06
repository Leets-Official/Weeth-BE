package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class InvalidUserOrderException extends BusinessLogicException {
	public InvalidUserOrderException() {super(400, "올바른 유저 조회 순서가 아닙니다.");}
}
