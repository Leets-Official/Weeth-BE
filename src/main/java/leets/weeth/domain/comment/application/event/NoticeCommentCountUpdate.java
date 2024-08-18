package leets.weeth.domain.comment.application.event;

import leets.weeth.domain.board.domain.entity.Notice;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoticeCommentCountUpdate {

    private final Notice notice;

    public Notice getNotice() {
        return notice;
    }

}