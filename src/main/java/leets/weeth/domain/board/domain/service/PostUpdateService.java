package leets.weeth.domain.board.domain.service;

import jakarta.transaction.Transactional;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.mapper.PostMapper;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.repository.PostRepository;
import leets.weeth.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostUpdateService {

    @Transactional
    public void update(Post post, PostDTO.Update dto, List<String> fileUrls){
        post.update(dto, fileUrls);
    }

}
