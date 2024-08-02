package leets.weeth.domain.schedule.application.usecase;

import leets.weeth.domain.schedule.domain.service.EventGetService;
import leets.weeth.domain.schedule.domain.service.MeetingGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static leets.weeth.domain.schedule.application.dto.ScheduleDTO.Response;

@Service
@RequiredArgsConstructor
public class ScheduleUseCaseImpl implements ScheduleUseCase {

    private final EventGetService eventGetService;
    private final MeetingGetService meetingGetService;

    @Override
    public List<Response> findByMonthly(LocalDateTime start, LocalDateTime end) {
        List<Response> schedules = eventGetService.find(start, end);
        schedules.addAll(meetingGetService.find(start, end));
        return schedules;
    }

    @Override
    public Map<Integer, List<Response>> findByYearly(LocalDateTime start, LocalDateTime end) {
        List<Response> schedules = eventGetService.find(start, end);
        schedules.addAll(meetingGetService.find(start, end));

        return schedules.stream()
                .flatMap(schedule -> {
                    List<Map.Entry<Integer, Response>> monthEventPairs = new ArrayList<>();

                    int left = schedule.start().getMonthValue();
                    int right = schedule.end().getMonthValue() + 1;
                    IntStream.range(left, right)
                            .forEach(month -> monthEventPairs.add(
                                    new AbstractMap.SimpleEntry<>(month, schedule))
                            );

                    return monthEventPairs.stream();
                })
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }
}
