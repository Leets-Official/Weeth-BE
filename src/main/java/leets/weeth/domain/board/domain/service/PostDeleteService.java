package leets.weeth.domain.board.domain.service;

import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDeleteService {

    private final PostRepository postRepository;

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

}
