package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;

public interface PostUsecase {

    void save(PostDTO.Save request, Long userId);

    void update(Long postId, PostDTO.Update dto, Long userId) throws UserNotMatchException;

    void delete(Long postId, Long userId) throws UserNotMatchException;

}
