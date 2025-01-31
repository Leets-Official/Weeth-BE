package leets.weeth.domain.schedule.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.schedule.application.dto.MeetingDTO;
import leets.weeth.domain.schedule.application.usecase.MeetingUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static leets.weeth.domain.schedule.presentation.ResponseMessage.MEETING_FIND_SUCCESS;

@Tag(name = "MEETING", description = "정기모임 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetings")
public class MeetingController {

    private final MeetingUseCase meetingUseCase;

    @GetMapping("/{meetingId}")
    @Operation(summary="정기모임 상세 조회")
    public CommonResponse<MeetingDTO.Response> find(@PathVariable Long meetingId) {
        return CommonResponse.createSuccess(MEETING_FIND_SUCCESS.getMessage(),meetingUseCase.find(meetingId));
    }
}
