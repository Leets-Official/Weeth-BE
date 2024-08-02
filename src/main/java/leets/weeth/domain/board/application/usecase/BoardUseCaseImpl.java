package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.BoardDTO;
import leets.weeth.domain.board.application.mapper.NoticeMapper;
import leets.weeth.domain.board.application.mapper.PostMapper;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.service.NoticeFindService;
import leets.weeth.domain.board.domain.service.PostFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardUseCaseImpl implements BoardUseCase {

    private final NoticeFindService noticeFindService;
    private final PostFindService postFindService;
    private final NoticeMapper noticeMapper;
    private final PostMapper postMapper;

    @Override
    public List<BoardDTO.Response> findNotices() {
        List<Notice> notices = noticeFindService.find();

        return notices.stream()
                .map(noticeMapper::to)
                .toList();
    }

    @Override
    public List<BoardDTO.Response> findPosts() {
        List<Post> posts = postFindService.find();

        return posts.stream()
                .map(postMapper::to)
                .toList();
    }

}
