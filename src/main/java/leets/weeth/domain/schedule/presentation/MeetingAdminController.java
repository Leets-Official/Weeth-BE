package leets.weeth.domain.schedule.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.schedule.application.usecase.MeetingUseCase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Save;
import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Update;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/meetings")
public class MeetingAdminController {

    private final MeetingUseCase meetingUseCase;

    @PostMapping
    public CommonResponse<Void> save(@RequestBody @Valid Save dto, @CurrentUser Long userId) {
        meetingUseCase.save(dto, userId);
        return CommonResponse.createSuccess();
    }

    @PatchMapping("/{meetingId}")
    public CommonResponse<Void> update(@RequestBody @Valid Update dto, @CurrentUser Long userId, @PathVariable Long meetingId) {
        meetingUseCase.update(dto, userId, meetingId);
        return CommonResponse.createSuccess();
    }

    @DeleteMapping("/{meetingId}")
    public CommonResponse<Void> delete(@PathVariable Long meetingId) {
        meetingUseCase.delete(meetingId);
        return CommonResponse.createSuccess();
    }
}
