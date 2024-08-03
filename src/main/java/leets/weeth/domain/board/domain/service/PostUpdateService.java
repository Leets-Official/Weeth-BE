package leets.weeth.domain.board.domain.service;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.mapper.PostMapper;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.repository.PostRepository;
import leets.weeth.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUpdateService {

    private final PostRepository postRepository;
    private final PostMapper mapper;

    public void update(Long postId, PostDTO.Update dto, User user) {
        Post post = mapper.update(postId, dto, user);
        postRepository.save(post);
    }

}
