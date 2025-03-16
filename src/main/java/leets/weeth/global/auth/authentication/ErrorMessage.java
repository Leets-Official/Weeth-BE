package leets.weeth.global.auth.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    UNAUTHORIZED(401, "인증 정보가 존재하지 않습니다."),
    FORBIDDEN(403, "권한이 없습니다.");

    private final int code;
    private final String message;
}
