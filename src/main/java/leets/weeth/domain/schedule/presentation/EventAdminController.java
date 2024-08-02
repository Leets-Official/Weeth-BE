package leets.weeth.domain.schedule.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.schedule.application.dto.EventDTO;
import leets.weeth.domain.schedule.application.usecase.EventUseCase;
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
    public CommonResponse<Void> save(@Valid @RequestBody Save dto) {
        eventUseCase.save(dto);
        return CommonResponse.createSuccess();
    }

    @PatchMapping("/{eventId}")
    public CommonResponse<Void> update(@PathVariable Long eventId, @Valid @RequestBody EventDTO.Update dto) {
        eventUseCase.update(eventId, dto);
        return CommonResponse.createSuccess();
    }
}
