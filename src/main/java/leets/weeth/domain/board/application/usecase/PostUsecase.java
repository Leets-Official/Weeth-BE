package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.global.common.error.exception.custom.LastPostFoundException;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostUsecase {

    void save(PostDTO.Save request, List<MultipartFile> files, Long userId);

    PostDTO.Response findPost(Long postId);

    List<PostDTO.ResponseAll> findPosts(Long postId, Integer count) throws LastPostFoundException;

    void update(Long postId, PostDTO.Update dto, List<MultipartFile> files, Long userId) throws UserNotMatchException;

    void delete(Long postId, Long userId) throws UserNotMatchException;

}
