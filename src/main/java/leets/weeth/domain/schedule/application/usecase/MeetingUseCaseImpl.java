package leets.weeth.domain.schedule.application.usecase;

import leets.weeth.domain.schedule.application.dto.MeetingDTO;
import leets.weeth.domain.schedule.application.mapper.MeetingMapper;
import leets.weeth.domain.schedule.domain.service.MeetingGetService;
import leets.weeth.domain.schedule.domain.service.MeetingSaveService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Response;

@Service
@RequiredArgsConstructor
public class MeetingUseCaseImpl implements MeetingUseCase {

    private final MeetingGetService meetingGetService;
    private final MeetingMapper mapper;
    private final MeetingSaveService meetingSaveService;
    private final UserGetService userGetService;

    @Override
    public Response find(Long meetingId) {
        return mapper.to(meetingGetService.find(meetingId));
    }

    @Override
    public void save(MeetingDTO.Save dto, Long userId) {
        User user = userGetService.find(userId);
        meetingSaveService.save(mapper.from(dto, user));
    }

}
