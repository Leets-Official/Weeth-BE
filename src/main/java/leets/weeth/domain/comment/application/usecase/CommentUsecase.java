package leets.weeth.domain.comment.application.usecase;

import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;

public interface CommentUsecase {

    void savePostComment(CommentDTO.Save dto, Long postId, Long userId);

    void saveNoticeComment(CommentDTO.Save dto, Long noticeId, Long userId);

    void updatePostComment(CommentDTO.Update dto, Long postId, Long commentId, Long userId) throws UserNotMatchException;

    void updateNoticeComment(CommentDTO.Update dto, Long noticeId, Long commentId, Long userId) throws UserNotMatchException;

    void deletePostComment(Long commentId, Long userId);

    void deleteNoticeComment(Long commentId, Long userId);

}
