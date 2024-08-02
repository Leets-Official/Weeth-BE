package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;

public interface NoticeUsecase {

    void save(NoticeDTO.Save request, Long userId);
}
