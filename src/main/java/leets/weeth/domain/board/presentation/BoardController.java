package leets.weeth.domain.board.presentation;

import leets.weeth.domain.board.application.dto.BoardDTO;
import leets.weeth.domain.board.application.usecase.BoardUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class BoardController {

    private final BoardUseCase boardUseCase;

    @GetMapping("/notices")
    public CommonResponse<List<BoardDTO.Response>> findNotices() {
        return CommonResponse.createSuccess(boardUseCase.findNotices());
    }

    @GetMapping("/posts")
    public CommonResponse<List<BoardDTO.Response>> findPosts() {
        return CommonResponse.createSuccess(boardUseCase.findPosts());
    }

}
