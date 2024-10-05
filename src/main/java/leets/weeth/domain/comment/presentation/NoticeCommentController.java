package leets.weeth.domain.comment.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.usecase.NoticeCommentUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static leets.weeth.domain.comment.domain.entity.enums.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices/{noticeId}/comments")
public class NoticeCommentController {

    private final NoticeCommentUsecase noticeCommentUsecase;

    @PostMapping
    public CommonResponse<String> saveNoticeComment(@RequestBody @Valid CommentDTO.Save dto, @PathVariable Long noticeId, @CurrentUser Long userId) {
        noticeCommentUsecase.saveNoticeComment(dto, noticeId, userId);
        return CommonResponse.createSuccess(COMMENT_CREATED_SUCCESS.getMessage());
    }

    @PatchMapping("{commentId}")
    public CommonResponse<String> updateNoticeComment(@RequestBody @Valid CommentDTO.Update dto, @PathVariable Long noticeId, @PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        noticeCommentUsecase.updateNoticeComment(dto, noticeId, commentId, userId);
        return CommonResponse.createSuccess(COMMENT_UPDATED_SUCCESS.getMessage());
    }

    @DeleteMapping("{commentId}")
    public CommonResponse<String> deleteNoticeComment(@PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        noticeCommentUsecase.deleteNoticeComment(commentId, userId);
        return CommonResponse.createSuccess(COMMENT_DELETED_SUCCESS.getMessage());
    }

}
