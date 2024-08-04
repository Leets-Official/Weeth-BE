package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.BoardDTO;

import java.util.List;

public interface BoardUseCase {

    BoardDTO.Response findNotice(Long noticeId);

    List<BoardDTO.Response> findNotices();

    BoardDTO.Response findPost(Long postId);

    List<BoardDTO.Response> findPosts();

}
