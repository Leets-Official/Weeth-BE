package leets.weeth.domain.board.domain.service;

import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.repository.PostRepository;
import leets.weeth.domain.board.application.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    public Slice<Post> findRecentPosts(Pageable pageable) {
        return postRepository.findPageBy(pageable);
    }

    public Slice<Post> search(String keyword, Pageable pageable) {
        return postRepository.findByTitleContainingOrContentContainingIgnoreCase(keyword, keyword, pageable);
    }

}
