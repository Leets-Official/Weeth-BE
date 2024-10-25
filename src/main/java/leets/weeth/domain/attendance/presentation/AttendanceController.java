package leets.weeth.domain.attendance.presentation;

import leets.weeth.domain.attendance.application.dto.AttendanceDTO;
import leets.weeth.domain.attendance.application.usecase.AttendanceUseCase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.domain.attendance.application.exception.AttendanceCodeMismatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static leets.weeth.domain.attendance.application.dto.AttendanceDTO.*;
import static leets.weeth.domain.attendance.presentation.ResponseMessage.ATTENDANCE_CHECKIN_SUCCESS;
import static leets.weeth.domain.attendance.presentation.ResponseMessage.ATTENDANCE_FIND_ALL_SUCCESS;
import static leets.weeth.domain.attendance.presentation.ResponseMessage.ATTENDANCE_FIND_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    private final AttendanceUseCase attendanceUseCase;

    @PatchMapping
    public CommonResponse<Void> checkIn(@CurrentUser Long userId, @RequestBody AttendanceDTO.CheckIn checkIn) throws AttendanceCodeMismatchException {
        attendanceUseCase.checkIn(userId, checkIn.code());
        return CommonResponse.createSuccess(ATTENDANCE_CHECKIN_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<Main> find(@CurrentUser Long userId) {
        return CommonResponse.createSuccess(ATTENDANCE_FIND_SUCCESS.getMessage(), attendanceUseCase.find(userId));
    }

    @GetMapping("/detail")
    public CommonResponse<Detail> findAll(@CurrentUser Long userId) {
        return CommonResponse.createSuccess(ATTENDANCE_FIND_ALL_SUCCESS.getMessage(), attendanceUseCase.findAll(userId));
    }
}
