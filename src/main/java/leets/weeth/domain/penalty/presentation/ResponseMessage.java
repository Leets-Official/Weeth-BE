package leets.weeth.domain.penalty.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    PENALTY_ASSIGN_SUCCESS(200, "페널티가 성공적으로 부여되었습니다."),
    PENALTY_FIND_ALL_SUCCESS(200, "모든 패널티가 성공적으로 조회되었습니다."),
    PENALTY_DELETE_SUCCESS(200, "패널티가 성공적으로 삭제되었습니다."),
    PENALTY_USER_FIND_SUCCESS(200, "패널티가 성공적으로 조회되었습니다.");


    private final int statusCode;
    private final String message;
}
