package leets.weeth.domain.schedule.presentation;

import leets.weeth.domain.schedule.application.usecase.ScheduleUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static leets.weeth.domain.schedule.application.dto.ScheduleDTO.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleUseCase scheduleUseCase;

    @GetMapping("/monthly")
    public CommonResponse<List<Response>> findByMonthly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return CommonResponse.createSuccess(scheduleUseCase.findByMonthly(start, end));
    }

    @GetMapping("/yearly")
    public CommonResponse<Map<Integer, List<Response>>> findByYearly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return CommonResponse.createSuccess(scheduleUseCase.findByYearly(start, end));
    }
}
