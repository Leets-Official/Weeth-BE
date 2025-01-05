package leets.weeth.domain.user.application.exception;

import leets.weeth.global.common.exception.BusinessLogicException;

public class StatusNotFoundException extends BusinessLogicException {
	public StatusNotFoundException() {
		super(400, "존재하지 않는 status 입니다.");
	}
}
