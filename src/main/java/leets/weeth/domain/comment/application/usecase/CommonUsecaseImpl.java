package leets.weeth.domain.comment.application.usecase;

import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.service.PostFindService;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.comment.domain.entity.Comment;
import leets.weeth.domain.comment.domain.service.CommentFindService;
import leets.weeth.domain.comment.domain.service.CommentSaveService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonUsecaseImpl implements CommentUsecase{

    private final CommentSaveService commentSaveService;

    private final UserGetService userGetService;
    private final PostFindService postFindService;
    private final CommentFindService commentFindService;
    private final CommentMapper commentMapper;

    @Override
    public void save(CommentDTO.Save dto, Long postId, Long userId) {
        User user = userGetService.find(userId);
        Post post = postFindService.find(postId);
        Comment parentComment = null;

        if(!(dto.parentCommentId() == null)) {
            parentComment = commentFindService.find(dto.parentCommentId());
        }
        Comment comment = commentMapper.from(dto, post, user, parentComment);
        commentSaveService.save(comment);

        // 부모 댓글이 없다면 새 댓글로 추가
        if(parentComment == null)
            post.addComment(comment);
        else
            // 부모 댓글이 있다면 자녀 댓글로 추가
            parentComment.addChild(comment);
    }

}
