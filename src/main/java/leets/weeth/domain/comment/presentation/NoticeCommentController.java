package leets.weeth.domain.comment.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.usecase.NoticeCommentUsecase;
import leets.weeth.domain.comment.application.usecase.PostCommentUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices/{noticeId}/comments")
public class NoticeCommentController {
// TODO 공지댓글, 포스트 댓글 컨트롤러 분리하기
    private final NoticeCommentUsecase noticeCommentUsecase;

    @PostMapping
    public CommonResponse<String> saveNoticeComment(@RequestBody @Valid CommentDTO.Save dto, @PathVariable Long noticeId, @CurrentUser Long userId) {
        noticeCommentUsecase.saveNoticeComment(dto, noticeId, userId);
        return CommonResponse.createSuccess("댓글 생성 성공");
    }

    @PatchMapping("{commentId}")
    public CommonResponse<String> updateNoticeComment(@RequestBody @Valid CommentDTO.Update dto, @PathVariable Long noticeId, @PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        noticeCommentUsecase.updateNoticeComment(dto, noticeId, commentId, userId);
        return CommonResponse.createSuccess("댓글 수정 성공");
    }

    @DeleteMapping("{commentId}")
    public CommonResponse<String> deleteNoticeComment(@PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        noticeCommentUsecase.deleteNoticeComment(commentId, userId);
        return CommonResponse.createSuccess("댓글 삭제 성공");
    }

}
