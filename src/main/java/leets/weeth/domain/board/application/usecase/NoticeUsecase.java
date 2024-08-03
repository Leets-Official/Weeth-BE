package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;

public interface NoticeUsecase {

    void save(NoticeDTO.Save dto, Long userId);

    void update(Long noticeId, NoticeDTO.Update dto, Long userId) throws UserNotMatchException;

    void delete(Long noticeId, Long userId) throws UserNotMatchException;

}
