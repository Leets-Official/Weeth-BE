package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostUsecase {

    void save(PostDTO.Save request, List<MultipartFile> files, Long userId);

    void update(Long postId, PostDTO.Update dto, List<MultipartFile> files, Long userId) throws UserNotMatchException;

    void delete(Long postId, Long userId) throws UserNotMatchException;

}
