package leets.weeth.domain.board.domain.service;

import leets.weeth.domain.board.domain.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeDeleteService {

    private final NoticeRepository noticeRepository;

    public void delete(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

}
