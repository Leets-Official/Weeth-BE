package leets.weeth.domain.schedule.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.weeth.domain.schedule.application.dto.EventDTO;
import leets.weeth.domain.schedule.application.usecase.EventUseCase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Save;
import static leets.weeth.domain.schedule.presentation.ResponseMessage.EVENT_DELETE_SUCCESS;
import static leets.weeth.domain.schedule.presentation.ResponseMessage.EVENT_SAVE_SUCCESS;
import static leets.weeth.domain.schedule.presentation.ResponseMessage.EVENT_UPDATE_SUCCESS;

@Tag(name = "EventAdminController", description = "일정 관련 어드민 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/events")
public class EventAdminController {

    private final EventUseCase eventUseCase;

    @PostMapping
    @Operation(summary="일정 생성")
    public CommonResponse<Void> save(@Valid @RequestBody Save dto,
                                     @Parameter(hidden = true) @CurrentUser Long userId) {
        eventUseCase.save(dto, userId);
        return CommonResponse.createSuccess(EVENT_SAVE_SUCCESS.getMessage());
    }

    @PatchMapping("/{eventId}")
    @Operation(summary="일정 수정")
    public CommonResponse<Void> update(@PathVariable Long eventId, @Valid @RequestBody EventDTO.Update dto,
                                       @Parameter(hidden = true) @CurrentUser Long userId) {
        eventUseCase.update(eventId, dto, userId);
        return CommonResponse.createSuccess(EVENT_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary="일정 삭제")
    public CommonResponse<Void> delete(@PathVariable Long eventId) {
        eventUseCase.delete(eventId);
        return CommonResponse.createSuccess(EVENT_DELETE_SUCCESS.getMessage());
    }
}
