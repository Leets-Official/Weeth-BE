package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.exception.NoticeNotFoundException;
import leets.weeth.domain.board.application.mapper.NoticeMapper;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.service.NoticeDeleteService;
import leets.weeth.domain.board.domain.service.NoticeFindService;
import leets.weeth.domain.board.domain.service.NoticeSaveService;
import leets.weeth.domain.board.domain.service.NoticeUpdateService;
import leets.weeth.domain.file.application.dto.response.FileResponse;
import leets.weeth.domain.file.application.mapper.FileMapper;
import leets.weeth.domain.file.domain.entity.File;
import leets.weeth.domain.file.domain.service.FileDeleteService;
import leets.weeth.domain.file.domain.service.FileGetService;
import leets.weeth.domain.file.domain.service.FileSaveService;
import leets.weeth.domain.file.domain.service.FileUpdateService;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final FileGetService fileGetService;
    private final FileUpdateService fileUpdateService;
    private final FileDeleteService fileDeleteService;

    private final NoticeMapper mapper;
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public void save(NoticeDTO.Save request, Long userId) {
        User user = userGetService.find(userId);

        Notice notice = mapper.fromNoticeDto(request, user);
        noticeSaveService.save(notice);

        List<File> files = request.files().stream()
                .map(fileSaveRequest -> fileMapper.toFile(fileSaveRequest.fileName(), fileSaveRequest.fileUrl(), notice))
                .toList();

        fileSaveService.save(files);
    }

    @Override
    public NoticeDTO.Response findNotice(Long noticeId) {
        Notice notice = noticeFindService.find(noticeId);

        List<FileResponse> response = getFiles(noticeId).stream()
                .map(fileMapper::toFileResponse)
                .toList();

        return mapper.toNoticeDto(notice, response);
    }

    @Override
    public List<NoticeDTO.ResponseAll> findNotices(Long noticeId, Integer count) {

        Long finalNoticeId = noticeFindService.findFinalNoticeId();

        // 첫번째 요청인 경우
        if (noticeId == null) {
            noticeId = finalNoticeId + 1;
        }

        // postId가 1 이하이거나 최대값보다 클경우
        if (noticeId < 1 || noticeId > finalNoticeId + 1) {
            throw new NoticeNotFoundException();
        }

        Pageable pageable = PageRequest.of(0, count); // 첫 페이지, 페이지당 15개 게시글

        List<Notice> notices = noticeFindService.findRecentNotices(noticeId, pageable);

        return notices.stream()
                .map(mapper::toAll)
                .toList();
    }

    @Override
    @Transactional
    public void update(Long noticeId, NoticeDTO.Update dto, Long userId) throws UserNotMatchException {
        Notice notice = validateOwner(noticeId, userId);

        List<File> fileList = getFiles(noticeId);

        fileUpdateService.updateFiles(fileList, dto.files());

        noticeUpdateService.update(notice, dto);
    }

    @Override
    @Transactional
    public void delete(Long noticeId, Long userId) throws UserNotMatchException {
        validateOwner(noticeId, userId);

        List<File> fileList = getFiles(noticeId);
        fileDeleteService.delete(fileList);

        noticeDeleteService.delete(noticeId);
    }

    private List<File> getFiles(Long noticeId) {
        return fileGetService.findAllByNotice(noticeId);
    }

    private Notice validateOwner(Long noticeId, Long userId) throws UserNotMatchException {
        Notice notice = noticeFindService.find(noticeId);
        if (!notice.getUser().getId().equals(userId)) {
            throw new UserNotMatchException();
        }
        return notice;
    }

}
