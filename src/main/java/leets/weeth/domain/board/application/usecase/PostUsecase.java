package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;

public interface PostUsecase {

    void save(PostDTO.Save request, Long userId);
}
