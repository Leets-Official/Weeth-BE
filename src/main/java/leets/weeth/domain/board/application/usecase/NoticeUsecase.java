package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import org.springframework.data.domain.Slice;


public interface NoticeUsecase {

    void save(NoticeDTO.Save dto, Long userId);

    NoticeDTO.Response findNotice(Long noticeId);

    Slice<NoticeDTO.ResponseAll> findNotices(Integer pageNumber, Integer pageSize);

    void update(Long noticeId, NoticeDTO.Update dto, Long userId) throws UserNotMatchException;

    void delete(Long noticeId, Long userId) throws UserNotMatchException;

}
