package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.BoardDTO;

import java.util.List;

public interface BoardUseCase {

    List<BoardDTO.Response> findNotices();

    List<BoardDTO.Response> findPosts();

}
