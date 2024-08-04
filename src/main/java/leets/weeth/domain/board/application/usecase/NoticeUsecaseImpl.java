package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.mapper.NoticeMapper;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.service.NoticeDeleteService;
import leets.weeth.domain.board.domain.service.NoticeFindService;
import leets.weeth.domain.board.domain.service.NoticeSaveService;
import leets.weeth.domain.board.domain.service.NoticeUpdateService;
import leets.weeth.domain.file.service.FileSaveService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeUsecaseImpl implements NoticeUsecase {

    private final NoticeSaveService noticeSaveService;
    private final NoticeFindService noticeFindService;
    private final NoticeUpdateService noticeUpdateService;
    private final NoticeDeleteService noticeDeleteService;
    private final UserGetService userGetService;
    private final FileSaveService fileSaveService;
    private final NoticeMapper mapper;

    @Override
    public void save(NoticeDTO.Save request, List<MultipartFile> files, Long userId) {
        User user = userGetService.find(userId);

        List<String> fileUrls;
        fileUrls = fileSaveService.uploadFiles(files);
        noticeSaveService.save(mapper.from(request, fileUrls, user));
    }

    @Override
    public void update(Long noticeId, NoticeDTO.Update dto, List<MultipartFile> files, Long userId) throws UserNotMatchException {
        Notice notice = validateOwner(noticeId, userId);

        List<String> fileUrls;
        fileUrls = fileSaveService.uploadFiles(files);
        noticeUpdateService.update(notice, dto, fileUrls);
    }

    @Override
    public void delete(Long noticeId, Long userId) throws UserNotMatchException {
        validateOwner(noticeId, userId);
        noticeDeleteService.delete(noticeId);
    }

    private Notice validateOwner(Long noticeId, Long userId) throws UserNotMatchException {
        Notice notice = noticeFindService.find(noticeId);
        if (!notice.getUser().getId().equals(userId)) {
            throw new UserNotMatchException();
        }
        return notice;
    }

}
