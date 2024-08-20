package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.global.common.error.exception.custom.LastNoticeFoundException;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticeUsecase {

    void save(NoticeDTO.Save dto, List<MultipartFile> files, Long userId);

    NoticeDTO.Response findNotice(Long noticeId);

    List<NoticeDTO.ResponseAll> findNotices(Long noticeId, Integer count) throws LastNoticeFoundException;

    void update(Long noticeId, NoticeDTO.Update dto, List<MultipartFile> files, Long userId) throws UserNotMatchException;

    void delete(Long noticeId, Long userId) throws UserNotMatchException;

}
