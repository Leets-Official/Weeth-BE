package leets.weeth.domain.schedule.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.schedule.application.dto.EventDTO;
import leets.weeth.domain.schedule.application.usecase.EventUseCase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Save;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/events")
public class EventAdminController {

    private final EventUseCase eventUseCase;

    @PostMapping
    public CommonResponse<Void> save(@Valid @RequestBody Save dto, @CurrentUser Long userId) {
        eventUseCase.save(dto, userId);
        return CommonResponse.createSuccess(ResponseMessage.EVENT_SAVE_SUCCESS.getStatusCode(),
                ResponseMessage.EVENT_SAVE_SUCCESS.getMessage());
    }

    @PatchMapping("/{eventId}")
    public CommonResponse<Void> update(@PathVariable Long eventId, @Valid @RequestBody EventDTO.Update dto, @CurrentUser Long userId) {
        eventUseCase.update(eventId, dto, userId);
        return CommonResponse.createSuccess(ResponseMessage.EVENT_UPDATE_SUCCESS.getStatusCode(),
                ResponseMessage.EVENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{eventId}")
    public CommonResponse<Void> delete(@PathVariable Long eventId) {
        eventUseCase.delete(eventId);
        return CommonResponse.createSuccess(ResponseMessage.EVENT_DELETE_SUCCESS.getStatusCode(),
                ResponseMessage.EVENT_DELETE_SUCCESS.getMessage());
    }
}
