package leets.weeth.domain.schedule.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.schedule.application.usecase.EventUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Response;
import static leets.weeth.domain.schedule.presentation.ResponseMessage.EVENT_FIND_SUCCESS;

@Tag(name = "EventController", description = "일정 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventUseCase eventUseCase;

    @GetMapping("/{eventId}")
    @Operation(summary="일정 상세 조회")
    public CommonResponse<Response> find(@PathVariable Long eventId) {
        return CommonResponse.createSuccess(EVENT_FIND_SUCCESS.getMessage(),
                eventUseCase.find(eventId));
    }

}
