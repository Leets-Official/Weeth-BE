package leets.weeth.domain.schedule.domain.service;

import jakarta.transaction.Transactional;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Update;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingUpdateService {

    public void update(Update dto, User user, Meeting meeting) {
        meeting.update(dto, user);
    }
}
