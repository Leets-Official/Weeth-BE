package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.mapper.NoticeMapper;
import leets.weeth.domain.board.application.mapper.PostMapper;
import leets.weeth.domain.board.domain.service.PostSaveService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostUserCaseImpl implements PostUsecase{

    private final PostSaveService postSaveService;
    private final UserGetService userGetService;
    private final PostMapper mapper;

    @Override
    public void save(PostDTO.Save request, Long userId) {
        User user = userGetService.find(userId);
        postSaveService.save(mapper.from(request, user));
    }
}
