package leets.weeth.domain.schedule.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.schedule.application.dto.MeetingDTO;
import leets.weeth.domain.schedule.application.usecase.MeetingUseCase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Save;
import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Update;
import static leets.weeth.domain.schedule.presentation.ResponseMessage.MEETING_ALL_FIND_SUCCESS;
import static leets.weeth.domain.schedule.presentation.ResponseMessage.MEETING_CARDINAL_FIND_SUCCESS;
import static leets.weeth.domain.schedule.presentation.ResponseMessage.MEETING_DELETE_SUCCESS;
import static leets.weeth.domain.schedule.presentation.ResponseMessage.MEETING_SAVE_SUCCESS;
import static leets.weeth.domain.schedule.presentation.ResponseMessage.MEETING_UPDATE_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/meetings")
public class MeetingAdminController {

    private final MeetingUseCase meetingUseCase;

    @PostMapping
    public CommonResponse<Void> save(@RequestBody @Valid Save dto, @CurrentUser Long userId) {
        meetingUseCase.save(dto, userId);
        return CommonResponse.createSuccess(MEETING_SAVE_SUCCESS.getMessage());
    }

    @PostMapping("/{cardinal}")
    public CommonResponse<List<MeetingDTO.ResponseAll>> findAll(@PathVariable Integer cardinal) {
        return CommonResponse.createSuccess(MEETING_CARDINAL_FIND_SUCCESS.getMessage(),meetingUseCase.findAll(cardinal));

    }

    @GetMapping
    public CommonResponse<List<MeetingDTO.ResponseAll>> findAll() {
        return CommonResponse.createSuccess(MEETING_ALL_FIND_SUCCESS.getMessage(),meetingUseCase.findAll());
    }

    @PatchMapping("/{meetingId}")
    public CommonResponse<Void> update(@RequestBody @Valid Update dto, @CurrentUser Long userId, @PathVariable Long meetingId) {
        meetingUseCase.update(dto, userId, meetingId);
        return CommonResponse.createSuccess(MEETING_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{meetingId}")
    public CommonResponse<Void> delete(@PathVariable Long meetingId) {
        meetingUseCase.delete(meetingId);
        return CommonResponse.createSuccess(MEETING_DELETE_SUCCESS.getMessage());
    }
}
