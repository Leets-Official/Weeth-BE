package leets.weeth.domain.attendance.presentation;

import static leets.weeth.domain.attendance.presentation.ResponseMessage.ATTENDANCE_CLOSE_SUCCESS;
import static leets.weeth.domain.attendance.presentation.ResponseMessage.ATTENDANCE_FIND_DETAIL_SUCCESS;
import static leets.weeth.domain.attendance.presentation.ResponseMessage.ATTENDANCE_UPDATED_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.attendance.application.dto.AttendanceDTO.AttendanceInfo;
import leets.weeth.domain.attendance.application.usecase.AttendanceUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/{attendanceId}")
    @Operation(summary = "개별 출석 정보 조회")
    public CommonResponse<AttendanceInfo> getAttendance(@PathVariable Long attendanceId) {
        return CommonResponse.createSuccess(ATTENDANCE_FIND_DETAIL_SUCCESS.getMessage(), attendanceUseCase.findAttendance(attendanceId));
    }

    @PatchMapping("/{attendanceId}")
    @Operation(summary = "출석 상태 수정")
    public CommonResponse<Void> updateAttendanceStatus(@PathVariable Long attendanceId) {
        attendanceUseCase.updateAttendanceStatus(attendanceId);
        return CommonResponse.createSuccess(ATTENDANCE_UPDATED_SUCCESS.getMessage());
    }
}
