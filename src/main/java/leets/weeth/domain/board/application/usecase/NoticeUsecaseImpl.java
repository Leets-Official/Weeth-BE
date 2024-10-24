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
import leets.weeth.domain.board.application.exception.NoticeNotFoundException;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        noticeSaveService.save(mapper.fromNoticeDto(request, fileUrls, user));
    }

    @Override
    public NoticeDTO.Response findNotice(Long noticeId) {
        Notice notice = noticeFindService.find(noticeId);
        return mapper.toNoticeDto(notice);
    }

    @Override
    public List<NoticeDTO.ResponseAll> findNotices(Long noticeId, Integer count) {

        Long finalNoticeId = noticeFindService.findFinalNoticeId();

        // 첫번째 요청인 경우
        if(noticeId==null){
            noticeId = finalNoticeId + 1;
        }

        // postId가 1 이하이거나 최대값보다 클경우
        if(noticeId < 1 || noticeId > finalNoticeId + 1){
            throw new NoticeNotFoundException();
        }

        Pageable pageable = PageRequest.of(0, count); // 첫 페이지, 페이지당 15개 게시글

        List<Notice> notices = noticeFindService.findRecentNotices(noticeId, pageable);

        return notices.stream()
                .map(mapper::toAll)
                .toList();
    }

    @Override
    public void update(Long noticeId, NoticeDTO.Update dto, List<MultipartFile> files, Long userId) throws UserNotMatchException {
        Notice notice = validateOwner(noticeId, userId);

        List<String> fileUrls = notice.getFileUrls();
        List<String> uploadedFileUrls = fileSaveService.uploadFiles(files);

        fileUrls.addAll(uploadedFileUrls);

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
