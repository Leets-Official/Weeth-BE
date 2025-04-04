package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import org.springframework.data.domain.Slice;


public interface PostUsecase {

    void save(PostDTO.Save request, Long userId);

    PostDTO.Response findPost(Long postId);

    Slice<PostDTO.ResponseAll> findPosts(int pageNumber, int pageSize);

    void update(Long postId, PostDTO.Update dto, Long userId) throws UserNotMatchException;

    void delete(Long postId, Long userId) throws UserNotMatchException;

    Slice<PostDTO.ResponseAll> searchPost(String keyword, int pageNumber, int pageSize);
}
