package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.exception.PageNotFoundException;
import leets.weeth.domain.board.application.mapper.NoticeMapper;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.service.NoticeDeleteService;
import leets.weeth.domain.board.domain.service.NoticeFindService;
import leets.weeth.domain.board.domain.service.NoticeSaveService;
import leets.weeth.domain.board.domain.service.NoticeUpdateService;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.domain.entity.Comment;
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
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        List<File> files = fileMapper.toFileList(request.files(), notice);
        fileSaveService.save(files);
    }

    @Override
    public NoticeDTO.Response findNotice(Long noticeId) {
        Notice notice = noticeFindService.find(noticeId);

        List<FileResponse> response = getFiles(noticeId).stream()
                .map(fileMapper::toFileResponse)
                .toList();

        return mapper.toNoticeDto(notice, response, filterParentComments(notice.getComments()));
    }

    @Override
    public Slice<NoticeDTO.ResponseAll> findNotices(int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            throw new PageNotFoundException();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")); // id를 기준으로 내림차순
        Slice<Notice> notices = noticeFindService.findRecentNotices(pageable);
        return notices.map(notice->mapper.toAll(notice, checkFileExistsByNotice(notice.id)));
    }

    @Override
    @Transactional
    public void update(Long noticeId, NoticeDTO.Update dto, Long userId) {
        Notice notice = validateOwner(noticeId, userId);

        List<File> fileList = getFiles(noticeId);
        fileDeleteService.delete(fileList);

        List<File> files = fileMapper.toFileList(dto.files(), notice);
        fileSaveService.save(files);

        noticeUpdateService.update(notice, dto);
    }

    @Override
    @Transactional
    public void delete(Long noticeId, Long userId) {
        validateOwner(noticeId, userId);

        List<File> fileList = getFiles(noticeId);
        fileDeleteService.delete(fileList);

        noticeDeleteService.delete(noticeId);
    }

    private List<File> getFiles(Long noticeId) {
        return fileGetService.findAllByNotice(noticeId);
    }

    private Notice validateOwner(Long noticeId, Long userId) {
        Notice notice = noticeFindService.find(noticeId);
        if (!notice.getUser().getId().equals(userId)) {
            throw new UserNotMatchException();
        }
        return notice;
    }

    private boolean checkFileExistsByNotice(Long noticeId){
        return !fileGetService.findAllByNotice(noticeId).isEmpty();
    }

    private List<CommentDTO.Response> filterParentComments(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }

        // 부모 댓글만 필터링하고, 각 부모 댓글에 대해 자식 댓글을 매핑
        return comments.stream()
            .filter(comment -> comment.getParent() == null) // 부모 댓글만 필터링
            .map(this::mapCommentWithChildren) // 자식 댓글 포함하여 매핑
            .toList();
    }

    private CommentDTO.Response mapCommentWithChildren(Comment comment) {
        if (comment == null) {
            return null;
        }

        // 기본 댓글 정보 매핑
        CommentDTO.Response.ResponseBuilder response = CommentDTO.Response.builder();

        response.name(comment.getUser().getName());
        response.time(comment.getModifiedAt());
        response.id(comment.getId());
        response.content(comment.getContent());

        // 자식 댓글들을 재귀적으로 매핑하여 children 필드에 설정
        List<CommentDTO.Response> childrenResponses = comment.getChildren().stream()
            .map(this::mapCommentWithChildren) // 자식 댓글도 동일하게 처리
            .collect(Collectors.toList());
        response.children(childrenResponses);

        return response.build();
    }

}
