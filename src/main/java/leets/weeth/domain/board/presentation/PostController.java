package leets.weeth.domain.board.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.usecase.PostUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static leets.weeth.domain.board.presentation.ResponseMessage.*;

@Tag(name = "PostController", description = "게시글 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostUsecase postUsecase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="게시글 생성")
    public CommonResponse<String> save(@RequestPart @Valid PostDTO.Save dto,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                       @Parameter(hidden = true) @CurrentUser Long userId) {
        postUsecase.save(dto, files, userId);
        return CommonResponse.createSuccess(POST_CREATED_SUCCESS.getMessage());
    }

    @GetMapping
    @Operation(summary="최근 게시글 조회 및 입력된 개수 만큼 조회")
    public CommonResponse<List<PostDTO.ResponseAll>> findPosts(@RequestParam(required = false) Long postId, @RequestParam Integer count) {
        return CommonResponse.createSuccess(POST_FIND_ALL_SUCCESS.getMessage(), postUsecase.findPosts(postId, count));
    }

    @GetMapping("/{postId}")
    @Operation(summary="특정 게시글 조회")
    public CommonResponse<PostDTO.Response> findPost(@PathVariable Long postId) {
        return CommonResponse.createSuccess(POST_FIND_BY_ID_SUCCESS.getMessage(),postUsecase.findPost(postId));
    }

    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="특정 게시글 수정")
    public CommonResponse<String> update(@PathVariable Long postId,
                                         @RequestPart @Valid PostDTO.Update dto,
                                         @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                         @Parameter(hidden = true) @CurrentUser Long userId) throws UserNotMatchException {
        postUsecase.update(postId, dto, files, userId);
        return CommonResponse.createSuccess(POST_UPDATED_SUCCESS.getMessage());
    }

    @DeleteMapping("/{postId}")
    @Operation(summary="특정 게시글 삭제")
    public CommonResponse<String> delete(@PathVariable Long postId, @Parameter(hidden = true) @CurrentUser Long userId) throws UserNotMatchException {
        postUsecase.delete(postId, userId);
        return CommonResponse.createSuccess(POST_DELETED_SUCCESS.getMessage());
    }

}
