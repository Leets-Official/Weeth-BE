package leets.weeth.domain.board.domain.service;

import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.repository.NoticeRepository;
import leets.weeth.global.common.error.exception.custom.NoticeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeFindService {

    private final NoticeRepository noticeRepository;

    public Notice find(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);
    }

    public List<Notice> find() {
        return noticeRepository.findAll();
    }

    public Long findFinalNoticeId(){
        return noticeRepository.findLastId()
                .orElseThrow(NoticeNotFoundException::new);
    }

    public Long findFirstNoticeId(){
        return noticeRepository.findFirstId()
                .orElseThrow(NoticeNotFoundException::new);
    }

    public List<Notice> findRecentNotices(Long postId, Pageable pageable) {
        return noticeRepository.findRecentNotices(postId, pageable);
    }

}
