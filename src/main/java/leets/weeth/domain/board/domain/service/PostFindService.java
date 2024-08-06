package leets.weeth.domain.board.domain.service;

import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.repository.PostRepository;
import leets.weeth.global.common.error.exception.custom.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostFindService {

    private final PostRepository postRepository;

    public Post find(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public List<Post> find(){
        return postRepository.findAll();
    }

    public Long findFinalPostId() {
        return postRepository.findLastId();
    }

    public List<Post> findRecentPosts(Long postId, Pageable pageable) {
        return postRepository.findRecentPosts(postId, pageable);
    }

}
