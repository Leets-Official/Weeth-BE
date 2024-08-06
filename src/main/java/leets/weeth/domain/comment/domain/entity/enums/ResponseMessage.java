package leets.weeth.domain.comment.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {

    COMMENT_CREATED_SUCCESS("댓글 생성 성공."),
    COMMENT_UPDATED_SUCCESS("댓글 수정 성공."),
    COMMENT_DELETED_SUCCESS("댓글 삭제 성공.");

    private final String message;
}
