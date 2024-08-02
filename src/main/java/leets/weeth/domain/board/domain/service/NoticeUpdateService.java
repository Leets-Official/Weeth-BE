package leets.weeth.domain.board.domain.service;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.mapper.NoticeMapper;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.repository.NoticeRepository;
import leets.weeth.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeUpdateService {

    private final NoticeRepository noticeRepository;
    private final NoticeMapper mapper;

    public void update(Long noticeId ,NoticeDTO.Update dto, User user){
        Notice update = mapper.update(noticeId, dto, user);
        noticeRepository.save(update);
    }

}
