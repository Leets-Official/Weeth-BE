package leets.weeth.domain.attendance.presentation;

import static leets.weeth.domain.attendance.presentation.ResponseMessage.ATTENDANCE_CLOSE_SUCCESS;
import static leets.weeth.domain.attendance.presentation.ResponseMessage.ATTENDANCE_UPDATED_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.attendance.application.usecase.AttendanceUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "ATTENDANCE ADMIN", description = "[ADMIN] 출석 어드민 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/attendances")
public class AttendanceAdminController {

    private final AttendanceUseCase attendanceUseCase;

    @PatchMapping
    @Operation(summary="출석 마감")
    public CommonResponse<Void> close(@RequestParam LocalDate now, @RequestParam Integer cardinal) {
        attendanceUseCase.close(now, cardinal);
        return CommonResponse.createSuccess(ATTENDANCE_CLOSE_SUCCESS.getMessage());
    }

    @PatchMapping("/{attendanceId}")
    @Operation(summary = "출석 상태 수정")
    public CommonResponse<Void> updateAttendanceStatus(@PathVariable Long attendanceId) {
        attendanceUseCase.updateAttendanceStatus(attendanceId);
        return CommonResponse.createSuccess(ATTENDANCE_UPDATED_SUCCESS.getMessage());
    }
}
