package leets.weeth.domain.schedule.presentation;

import leets.weeth.domain.schedule.application.usecase.EventUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventUseCase eventUseCase;

    @GetMapping("/{eventId}")
    public CommonResponse<Response> find(@PathVariable Long eventId) {
        return CommonResponse.createSuccess(eventUseCase.find(eventId));
    }

}
