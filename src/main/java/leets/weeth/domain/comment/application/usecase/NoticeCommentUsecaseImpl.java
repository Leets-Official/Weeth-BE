package leets.weeth.domain.comment.application.usecase;

import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.service.NoticeFindService;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.comment.domain.entity.Comment;
import leets.weeth.domain.comment.domain.service.CommentDeleteService;
import leets.weeth.domain.comment.domain.service.CommentFindService;
import leets.weeth.domain.comment.domain.service.CommentSaveService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeCommentUsecaseImpl implements NoticeCommentUsecase {

    private final CommentSaveService commentSaveService;
    private final CommentFindService commentFindService;
    private final CommentDeleteService commentDeleteService;

    private final NoticeFindService noticeFindService;

    private final UserGetService userGetService;
    private final CommentMapper commentMapper;


    @Override
    @Transactional
    public void saveNoticeComment(CommentDTO.Save dto, Long noticeId, Long userId) {
        User user = userGetService.find(userId);
        Notice notice = noticeFindService.find(noticeId);
        Comment parentComment = null;

        if(!(dto.parentCommentId() == null)) {
            parentComment = commentFindService.find(dto.parentCommentId());
        }
        Comment comment = commentMapper.fromCommentDto(dto, notice, user, parentComment);
        commentSaveService.save(comment);

        // 부모 댓글이 없다면 새 댓글로 추가
        if(parentComment == null)
            notice.addComment(comment);
        else
            // 부모 댓글이 있다면 자녀 댓글로 추가
            parentComment.addChild(comment);

        notice.incrementCommentCount();
    }


    @Override
    @Transactional
    public void updateNoticeComment(CommentDTO.Update dto, Long noticeId, Long commentId, Long userId) throws UserNotMatchException {
        User user = userGetService.find(userId);
        Notice notice = noticeFindService.find(noticeId);
        Comment comment = validateOwner(commentId, userId);

        comment.update(dto);
    }

    @Override
    @Transactional
    public void deleteNoticeComment(Long commentId, Long userId) throws UserNotMatchException {
        User user = userGetService.find(userId);
        Comment comment = validateOwner(commentId, userId);
        Notice notice = comment.getNotice();

        /*
        1. 지우고자 하는 댓글이 맨 아래층인 경우(child, child가 없는 댓글
            - 현재 댓글.getChildren이 NULL 이면 해당
            - 내가 child인지 child가 없는 댓글인지 구분해야함
            - child인 경우 -> 부모가 있음. 하지만 부모를 삭제하는게 아니라 나만 삭제함, 부모의 childern에서 나를 제거해야함
            - child가 없는 댓글인 경우 -> 자식이 없기 떄문에 나만 삭제함
         */
        // 현재 삭제하고자 하는 댓글이 자식이 없는 경우
        if(comment.getChildren().isEmpty()){
            Comment parentComment = findParentComment(commentId);
            commentDeleteService.delete(commentId);
            if(parentComment != null){
                parentComment.getChildren().remove(comment);
                if(parentComment.getIsDeleted() && parentComment.getChildren().isEmpty()){
                    notice.getComments().remove(parentComment);
                    commentDeleteService.delete(parentComment.getId());
                }
            }
        } else{
            comment.markAsDeleted();
            commentSaveService.save(comment);
        }
        notice.decrementCommentCount();

    }

    private Comment findParentComment(Long commentId) {
        List<Comment> comments = commentFindService.find();
        for (Comment comment : comments) {
            if (comment.getChildren().stream().anyMatch(child -> child.getId().equals(commentId))) {
                return comment;
            }
        }
        return null; // 부모 댓글을 찾지 못한 경우
    }

    private Comment validateOwner(Long commentId, Long userId) throws UserNotMatchException {
        Comment comment = commentFindService.find(commentId);

        if (!comment.getUser().getId().equals(userId)) {
            throw new UserNotMatchException();
        }
        return comment;
    }

}
