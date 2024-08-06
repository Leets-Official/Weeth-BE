package leets.weeth.domain.penalty.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {

    PENALTY_ASSIGN_SUCCESS("패널티 부여 성공"),
    PENALTY_DELETE_SUCCESS("패널티 삭제 성공");

    private final String message;

}
