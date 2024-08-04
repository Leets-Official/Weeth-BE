package leets.weeth.domain.comment.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.usecase.PostCommentUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class PostCommentController {
// TODO 공지댓글, 포스트 댓글 컨트롤러 분리하기
    private final PostCommentUsecase postCommentUsecase;

    @PostMapping
    public CommonResponse<String> savePostComment(@RequestBody @Valid CommentDTO.Save dto, @PathVariable Long postId, @CurrentUser Long userId) {
        postCommentUsecase.savePostComment(dto, postId, userId);
        return CommonResponse.createSuccess("댓글 생성 성공");
    }

    @PatchMapping("/{commentId}")
    public CommonResponse<String> updatePostComment(@RequestBody @Valid CommentDTO.Update dto, @PathVariable Long postId, @PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        postCommentUsecase.updatePostComment(dto, postId, commentId, userId);
        return CommonResponse.createSuccess("댓글 수정 성공");
    }

    @DeleteMapping("{commentId}")
    public CommonResponse<String> deletePostComment(@PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        postCommentUsecase.deletePostComment(commentId, userId);
        return CommonResponse.createSuccess("댓글 삭제 성공");
    }

}
