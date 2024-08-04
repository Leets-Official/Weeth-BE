package leets.weeth.domain.comment.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.usecase.CommentUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
// TODO 공지댓글, 포스트 댓글 컨트롤러 분리하기
    private final CommentUsecase commentUsecase;
    // 댓글 생성
    @PostMapping("/posts/{postId}/comments")
    public CommonResponse<String> savePostComment(@RequestBody @Valid CommentDTO.Save dto, @PathVariable Long postId, @CurrentUser Long userId) {
        commentUsecase.savePostComment(dto, postId, userId);
        return CommonResponse.createSuccess("댓글 생성 성공");
    }

    @PostMapping("/notices/{noticeId}/comments")
    public CommonResponse<String> saveNoticeComment(@RequestBody @Valid CommentDTO.Save dto, @PathVariable Long noticeId, @CurrentUser Long userId) {
        commentUsecase.saveNoticeComment(dto, noticeId, userId);
        return CommonResponse.createSuccess("댓글 생성 성공");
    }

    // 댓글 수정
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public CommonResponse<String> updatePostComment(@RequestBody @Valid CommentDTO.Update dto, @PathVariable Long postId, @PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        commentUsecase.updatePostComment(dto, postId, commentId, userId);
        return CommonResponse.createSuccess("댓글 수정 성공");
    }

    @PatchMapping("/notices/{noticeId}/comments/{commentId}")
    public CommonResponse<String> updateNoticeComment(@RequestBody @Valid CommentDTO.Update dto, @PathVariable Long noticeId, @PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        commentUsecase.updateNoticeComment(dto, noticeId, commentId, userId);
        return CommonResponse.createSuccess("댓글 수정 성공");
    }
    // 댓글 삭제
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public CommonResponse<String> deletePostComment(@PathVariable Long commentId, @CurrentUser Long userId) {
        commentUsecase.deletePostComment(commentId, userId);
        return CommonResponse.createSuccess("댓글 삭제 성공");
    }

    @DeleteMapping("/notices/{noticeId}/comments/{commentId}")
    public CommonResponse<String> deleteNoticeComment(@PathVariable Long commentId, @CurrentUser Long userId) {
        commentUsecase.deleteNoticeComment(commentId, userId);
        return CommonResponse.createSuccess("댓글 삭제 성공");
    }

}
