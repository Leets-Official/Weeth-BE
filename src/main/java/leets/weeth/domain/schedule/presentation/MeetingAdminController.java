package leets.weeth.domain.schedule.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.schedule.application.usecase.MeetingUseCase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static leets.weeth.domain.schedule.application.dto.MeetingDTO.Save;

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
}
