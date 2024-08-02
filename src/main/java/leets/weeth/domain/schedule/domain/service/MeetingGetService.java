package leets.weeth.domain.schedule.domain.service;

import leets.weeth.domain.schedule.application.dto.ScheduleDTO;
import leets.weeth.domain.schedule.application.mapper.ScheduleMapper;
import leets.weeth.domain.schedule.domain.entity.Meeting;
import leets.weeth.domain.schedule.domain.repository.MeetingRepository;
import leets.weeth.global.common.error.exception.custom.MeetingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingGetService {

    private final MeetingRepository meetingRepository;
    private final ScheduleMapper mapper;

    public Meeting find(Long meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(MeetingNotFoundException::new);
    }

    public List<ScheduleDTO.Response> find(LocalDateTime start, LocalDateTime end) {
        return meetingRepository.findByStartLessThanEqualAndEndGreaterThanEqualOrderByStartAsc(end, start).stream()
                .map(meeting -> mapper.toScheduleDTO(meeting, true))
                .toList();
    }
}
