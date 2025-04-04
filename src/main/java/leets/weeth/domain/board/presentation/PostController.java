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
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;


import static leets.weeth.domain.board.presentation.ResponseMessage.*;

@Tag(name = "BOARD", description = "게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class PostController {

    private final PostUsecase postUsecase;

    @PostMapping
    @Operation(summary="게시글 생성")
    public CommonResponse<String> save(@RequestBody @Valid PostDTO.Save dto,
                                       @Parameter(hidden = true) @CurrentUser Long userId) {
        postUsecase.save(dto, userId);
        return CommonResponse.createSuccess(POST_CREATED_SUCCESS.getMessage());
    }

    @GetMapping
    @Operation(summary="게시글 목록 조회 [무한스크롤]")
    public CommonResponse<Slice<PostDTO.ResponseAll>> findPosts(@RequestParam("pageNumber") int pageNumber,
                                                                      @RequestParam("pageSize") int pageSize) {
        return CommonResponse.createSuccess(POST_FIND_ALL_SUCCESS.getMessage(), postUsecase.findPosts(pageNumber, pageSize));
    }

    @GetMapping("/{boardId}")
    @Operation(summary="특정 게시글 조회")
    public CommonResponse<PostDTO.Response> findPost(@PathVariable Long boardId) {
        return CommonResponse.createSuccess(POST_FIND_BY_ID_SUCCESS.getMessage(),postUsecase.findPost(boardId));
    }

    @GetMapping("/search")
    @Operation(summary="게시글 검색 [무한스크롤]")
    public CommonResponse<Slice<PostDTO.ResponseAll>> findPost(@RequestParam String keyword, @RequestParam("pageNumber") int pageNumber,
                                                                    @RequestParam("pageSize") int pageSize) {
        return CommonResponse.createSuccess(POST_FIND_BY_ID_SUCCESS.getMessage(),postUsecase.searchPost(keyword, pageNumber, pageSize));
    }

    @PatchMapping(value = "/{boardId}")
    @Operation(summary="특정 게시글 수정")
    public CommonResponse<String> update(@PathVariable Long boardId,
                                         @RequestBody @Valid PostDTO.Update dto,
                                         @Parameter(hidden = true) @CurrentUser Long userId) throws UserNotMatchException {
        postUsecase.update(boardId, dto, userId);
        return CommonResponse.createSuccess(POST_UPDATED_SUCCESS.getMessage());
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary="특정 게시글 삭제")
    public CommonResponse<String> delete(@PathVariable Long boardId, @Parameter(hidden = true) @CurrentUser Long userId) throws UserNotMatchException {
        postUsecase.delete(boardId, userId);
        return CommonResponse.createSuccess(POST_DELETED_SUCCESS.getMessage());
    }

}
