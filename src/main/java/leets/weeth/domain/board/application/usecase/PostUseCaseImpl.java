package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.mapper.PostMapper;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.service.PostDeleteService;
import leets.weeth.domain.board.domain.service.PostFindService;
import leets.weeth.domain.board.domain.service.PostSaveService;
import leets.weeth.domain.board.domain.service.PostUpdateService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.common.error.exception.custom.UserNotFoundException;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUseCaseImpl implements PostUsecase{

    private final PostSaveService postSaveService;
    private final PostFindService postFindService;
    private final PostUpdateService postUpdateService;
    private final PostDeleteService postDeleteService;
    private final UserGetService userGetService;
    private final PostMapper mapper;

    @Override
    public void save(PostDTO.Save request, Long userId) {
        User user = userGetService.find(userId);
        postSaveService.save(mapper.from(request, user));
    }

    @Override
    public void update(Long postId, PostDTO.Update dto, Long userId) throws UserNotMatchException {
        User user = validateOwner(postId, userId);
        postUpdateService.update(postId, dto, user);
    }

    @Override
    public void delete(Long postId, Long userId) throws UserNotMatchException {
        validateOwner(postId, userId);
        postDeleteService.delete(postId);
    }

    private User validateOwner(Long postId, Long userId) throws UserNotMatchException {
        Post post = postFindService.find(postId);
        User user = userGetService.find(userId);

        if (!post.getUser().equals(user)) {
            throw new UserNotMatchException();
        }
        return user;
    }

}
