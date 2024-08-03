package leets.weeth.domain.comment.application.usecase;

import leets.weeth.domain.comment.application.dto.CommentDTO;

public interface CommentUsecase {

    void save(CommentDTO.Save dto, Long postId, Long userId);
}
