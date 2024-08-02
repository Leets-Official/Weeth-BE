package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.mapper.NoticeMapper;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.service.NoticeDeleteService;
import leets.weeth.domain.board.domain.service.NoticeFindService;
import leets.weeth.domain.board.domain.service.NoticeSaveService;
import leets.weeth.domain.board.domain.service.NoticeUpdateService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeUsecaseImpl implements NoticeUsecase {

    private final NoticeSaveService noticeSaveService;
    private final NoticeFindService noticeFindService;
    private final NoticeUpdateService noticeUpdateService;
    private final NoticeDeleteService noticeDeleteService;
    private final UserGetService userGetService;
    private final NoticeMapper mapper;

    @Override
    public void save(NoticeDTO.Save request, Long userId) {
        User user = userGetService.find(userId);
        noticeSaveService.save(mapper.from(request, user));
    }

    @Override
    public void update(Long noticeId, NoticeDTO.Update dto, Long userId) throws UserNotMatchException {
        User user = validateOwner(noticeId, userId);
        noticeUpdateService.update(noticeId, dto, user);
    }

    @Override
    public void delete(Long noticeId, Long userId) throws UserNotMatchException {
        validateOwner(noticeId, userId);
        noticeDeleteService.delete(noticeId);
    }

    private User validateOwner(Long noticeId, Long userId) throws UserNotMatchException {
        Notice notice = noticeFindService.find(noticeId);
        User user = userGetService.find(userId);
        if (!notice.getUser().equals(user)) {
            throw new UserNotMatchException();
        }
        return user;
    }

}
