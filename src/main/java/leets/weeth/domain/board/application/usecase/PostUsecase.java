package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.user.domain.entity.User;

public interface PostUsecase {

    void save(PostDTO.Save request, User user);
}
