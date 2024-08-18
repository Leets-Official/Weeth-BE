package leets.weeth.domain.comment.application.event;

import leets.weeth.domain.board.domain.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostCommentCountUpdateEvent {

    private final Post post;

}
