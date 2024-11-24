package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticeUsecase {

    void save(NoticeDTO.Save dto, Long userId);

    NoticeDTO.Response findNotice(Long noticeId);

    List<NoticeDTO.ResponseAll> findNotices(Long noticeId, Integer count);

    void update(Long noticeId, NoticeDTO.Update dto, Long userId) throws UserNotMatchException;

    void delete(Long noticeId, Long userId) throws UserNotMatchException;

}
