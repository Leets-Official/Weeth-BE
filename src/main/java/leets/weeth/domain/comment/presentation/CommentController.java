package leets.weeth.domain.comment.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.application.mapper.CommentMapper;
import leets.weeth.domain.comment.application.usecase.CommentUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentUsecase commentUsecase;
    // 댓글 생성
    @PostMapping
    public CommonResponse<String> save(@RequestBody @Valid CommentDTO.Save dto, @PathVariable Long postId, @CurrentUser Long userId) {
        commentUsecase.save(dto, postId, userId);
        return CommonResponse.createSuccess("댓글 생성 성공");
    }

    // 댓글 수정

    // 댓글 삭제
}
