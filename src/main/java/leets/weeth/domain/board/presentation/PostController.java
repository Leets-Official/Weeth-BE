package leets.weeth.domain.board.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.usecase.PostUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static leets.weeth.domain.board.domain.entity.enums.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostUsecase postUsecase;

    @PostMapping
    public CommonResponse<String> save(@RequestPart @Valid PostDTO.Save dto,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                       @CurrentUser Long userId) {
        postUsecase.save(dto, files, userId);
        return CommonResponse.createSuccess(POST_CREATED_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<PostDTO.Response>> findPosts(@RequestParam(required = false) Long postId, @RequestParam Integer count) {
        return CommonResponse.createSuccess(postUsecase.findPosts(postId, count));
    }

    @GetMapping("/{postId}")
    public CommonResponse<PostDTO.Response> findPost(@PathVariable Long postId) {
        return CommonResponse.createSuccess(postUsecase.findPost(postId));
    }

    @PatchMapping("/{postId}")
    public CommonResponse<String> update(@PathVariable Long postId,
                                         @RequestPart @Valid PostDTO.Update dto,
                                         @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                         @CurrentUser Long userId) throws UserNotMatchException {
        postUsecase.update(postId, dto, files, userId);
        return CommonResponse.createSuccess(POST_UPDATED_SUCCESS.getMessage());
    }

    @DeleteMapping("/{postId}")
    public CommonResponse<String> delete(@PathVariable Long postId, @CurrentUser Long userId) throws UserNotMatchException {
        postUsecase.delete(postId, userId);
        return CommonResponse.createSuccess(POST_DELETED_SUCCESS.getMessage());
    }

}
