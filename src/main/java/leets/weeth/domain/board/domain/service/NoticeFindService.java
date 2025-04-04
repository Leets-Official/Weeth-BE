package leets.weeth.domain.board.domain.service;

import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.repository.NoticeRepository;
import leets.weeth.domain.board.application.exception.NoticeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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


    public Slice<Notice> findRecentNotices(Pageable pageable) {
        return noticeRepository.findPageBy(pageable);
    }

}
