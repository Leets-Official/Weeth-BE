package leets.weeth.domain.comment.domain.service;

import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.domain.entity.Comment;
import leets.weeth.domain.comment.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentSaveService {

    private final CommentRepository commentRepository;

    public void save(Comment comment){
        commentRepository.save(comment);
    }

}
