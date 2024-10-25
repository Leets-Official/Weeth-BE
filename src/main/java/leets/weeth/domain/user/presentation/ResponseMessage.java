package leets.weeth.domain.user.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    // UserAdminController 관련
    USER_FIND_ALL_SUCCESS(200, "관리자가 모든 회원 정보를 성공적으로 조회했습니다."),
    USER_ACCEPT_SUCCESS(200, "회원 가입 승인이 성공적으로 처리되었습니다."),
    USER_BAN_SUCCESS(200, "회원이 성공적으로 차단되었습니다."),
    USER_ROLE_UPDATE_SUCCESS(200, "회원의 역할이 성공적으로 수정되었습니다."),
    USER_APPLY_OB_SUCCESS(200, "OB 신청이 성공적으로 처리되었습니다."),
    USER_PASSWORD_RESET_SUCCESS(200, "비밀번호가 성공적으로 초기화되었습니다."),
    // UserController 관련
    USER_APPLY_SUCCESS(200, "회원 가입 신청이 성공적으로 처리되었습니다."),
    USER_EMAIL_CHECK_SUCCESS(200, "이메일 중복 검사가 성공적으로 처리되었습니다."),
    USER_FIND_BY_ID_SUCCESS(200, "회원 정보가 성공적으로 조회되었습니다."),
    USER_UPDATE_SUCCESS(200, "회원 정보가 성공적으로 수정되었습니다."),
    USER_LEAVE_SUCCESS(200, "회원 탈퇴가 성공적으로 처리되었습니다.");
    private final int statusCode;
    private final String message;
}
