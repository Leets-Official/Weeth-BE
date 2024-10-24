package leets.weeth.global.common.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {

    private final int statusCode;

    public BusinessLogicException(int code, String message) {
        super(message);
        this.statusCode = code;
    }
}
