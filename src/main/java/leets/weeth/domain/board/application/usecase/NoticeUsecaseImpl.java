package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.mapper.NoticeMapper;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.service.NoticeFindService;
import leets.weeth.domain.board.domain.service.NoticeSaveService;
import leets.weeth.domain.board.domain.service.NoticeUpdateService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeUsecaseImpl implements NoticeUsecase {

    private final NoticeSaveService noticeSaveService;
    private final NoticeFindService noticeFindService;
    private final NoticeUpdateService noticeUpdateService;
    private final UserGetService userGetService;
    private final NoticeMapper mapper;

    @Override
    public void save(NoticeDTO.Save request, Long userId) {
        User user = userGetService.find(userId);
        noticeSaveService.save(mapper.from(request, user));
    }

    @Override
    public void update(Long noticeId, NoticeDTO.Save dto, Long userId) {
        User user = userGetService.find(userId);
        Notice notice = noticeFindService.find(noticeId);

    }
}
