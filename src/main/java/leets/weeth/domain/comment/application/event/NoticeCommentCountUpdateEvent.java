package leets.weeth.domain.comment.application.event;

import leets.weeth.domain.board.domain.entity.Notice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NoticeCommentCountUpdateEvent {

    private final Notice notice;

}