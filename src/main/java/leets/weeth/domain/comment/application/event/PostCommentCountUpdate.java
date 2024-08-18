package leets.weeth.domain.comment.application.event;

import leets.weeth.domain.board.domain.entity.Post;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostCommentCountUpdate {

    private final Post post;

    public Post getPost(){
        return post;
    }

}
