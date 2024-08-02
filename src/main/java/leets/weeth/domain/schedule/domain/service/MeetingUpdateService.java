package leets.weeth.domain.schedule.domain.service;

import leets.weeth.domain.schedule.application.mapper.MeetingMapper;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.schedule.domain.repository.MeetingRepository;
import leets.weeth.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Update;

@Service
@RequiredArgsConstructor
public class MeetingUpdateService {

    private final MeetingRepository meetingRepository;
    private final MeetingMapper mapper;

    public void update(Update dto, User user, Long meetingId) {
        Meeting update = mapper.update(meetingId, dto, user);
        meetingRepository.save(update);
    }
}
