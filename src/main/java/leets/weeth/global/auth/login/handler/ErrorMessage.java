package leets.weeth.global.auth.login.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    LOGIN_FAIL(400, "로그인에 실패했습니다."),
    LOGIN_SUCCESS(200, "로그인에 성공했습니다.");

    private final int code;
    private final String message;
}