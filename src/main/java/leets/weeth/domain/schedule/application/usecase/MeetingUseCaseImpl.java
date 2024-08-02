package leets.weeth.domain.schedule.application.usecase;

import leets.weeth.domain.schedule.application.mapper.MeetingMapper;
import leets.weeth.domain.schedule.domain.service.MeetingGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Response;

@Service
@RequiredArgsConstructor
public class MeetingUseCaseImpl implements MeetingUseCase {

    private final MeetingGetService meetingGetService;
    private final MeetingMapper mapper;

    @Override
    public Response find(Long meetingId) {
        return mapper.to(meetingGetService.find(meetingId));
    }

}
