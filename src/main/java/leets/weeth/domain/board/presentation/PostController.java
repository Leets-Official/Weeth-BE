package leets.weeth.domain.board.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.usecase.PostUseCaseImpl;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostUseCaseImpl postUseCase;

    @PostMapping
    public CommonResponse<String> save(@RequestBody @Valid PostDTO.Save dto, @CurrentUser Long userId) {
        postUseCase.save(dto, userId);
        return CommonResponse.createSuccess("게시글 생성 성공");
    }

    @PatchMapping("/{postId}")
    public CommonResponse<String> update(@PathVariable Long postId, @RequestBody @Valid PostDTO.Update dto, @CurrentUser Long userId) throws UserNotMatchException {
        postUseCase.update(postId, dto, userId);
        return CommonResponse.createSuccess("게시글 수정 성공");
    }

    @DeleteMapping("/{postId}")
    public CommonResponse<String> delete(@PathVariable Long postId, @CurrentUser Long userId) throws UserNotMatchException {
        postUseCase.delete(postId, userId);
        return CommonResponse.createSuccess("게시글 삭제 성공");
    }

}
