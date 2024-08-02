package leets.weeth.domain.schedule.presentation;

import leets.weeth.domain.schedule.application.usecase.MeetingUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings")
public class MeetingController {

    private final MeetingUseCase meetingUseCase;

    @GetMapping("/{meetingId}")
    public CommonResponse<Response> find(@PathVariable Long meetingId) {
        return CommonResponse.createSuccess(meetingUseCase.find(meetingId));
    }
}
