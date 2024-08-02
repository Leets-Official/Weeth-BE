package leets.weeth.domain.board.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.usecase.PostUserCaseImpl;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostUserCaseImpl postUserCase;

    @PostMapping
    public CommonResponse<String> save(@RequestBody @Valid PostDTO.Save dto, @CurrentUser Long userId) {
        postUserCase.save(dto, userId);
        return CommonResponse.createSuccess("게시글 생성 성공");
    }
}
