package leets.weeth.domain.comment.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.usecase.PostCommentUsecase;
import leets.weeth.domain.comment.domain.entity.enums.ResponseMessage;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static leets.weeth.domain.comment.domain.entity.enums.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class PostCommentController {

    private final PostCommentUsecase postCommentUsecase;

    @PostMapping
    public CommonResponse<String> savePostComment(@RequestBody @Valid CommentDTO.Save dto, @PathVariable Long postId, @CurrentUser Long userId) {
        postCommentUsecase.savePostComment(dto, postId, userId);
        return CommonResponse.createSuccess(COMMENT_CREATED_SUCCESS.getMessage());
    }

    @PatchMapping("/{commentId}")
    public CommonResponse<String> updatePostComment(@RequestBody @Valid CommentDTO.Update dto, @PathVariable Long postId, @PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        postCommentUsecase.updatePostComment(dto, postId, commentId, userId);
        return CommonResponse.createSuccess(COMMENT_UPDATED_SUCCESS.getMessage());
    }

    @DeleteMapping("{commentId}")
    public CommonResponse<String> deletePostComment(@PathVariable Long commentId, @CurrentUser Long userId) throws UserNotMatchException {
        postCommentUsecase.deletePostComment(commentId, userId);
        return CommonResponse.createSuccess(COMMENT_DELETED_SUCCESS.getMessage());
    }

}
