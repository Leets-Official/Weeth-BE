package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.mapper.NoticeMapper;
import leets.weeth.domain.board.domain.service.NoticeSaveService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoticeUsecaseImpl implements NoticeUsecase {

    private final NoticeSaveService noticeSaveService;
    private final UserGetService userGetService;
    private final NoticeMapper mapper;

    @Override
    public void save(NoticeDTO.Save request, Long userId) {
        User user = userGetService.find(userId);
        noticeSaveService.save(mapper.from(request, user));
    }
}
