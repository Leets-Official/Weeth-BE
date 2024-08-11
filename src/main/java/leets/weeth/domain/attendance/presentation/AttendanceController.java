package leets.weeth.domain.attendance.presentation;

import leets.weeth.domain.attendance.application.dto.AttendanceDTO;
import leets.weeth.domain.attendance.application.usecase.AttendanceUseCase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.error.exception.custom.AttendanceCodeMismatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static leets.weeth.domain.attendance.application.dto.AttendanceDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    private final AttendanceUseCase attendanceUseCase;

    @PatchMapping
    public CommonResponse<Void> checkIn(@CurrentUser Long userId, @RequestBody AttendanceDTO.CheckIn checkIn) throws AttendanceCodeMismatchException {
        attendanceUseCase.checkIn(userId, checkIn.code());
        return CommonResponse.createSuccess();
    }

    @GetMapping
    public CommonResponse<Main> find(@CurrentUser Long userId) {
        return CommonResponse.createSuccess(attendanceUseCase.find(userId));
    }

    @GetMapping("/detail")
    public CommonResponse<Detail> findAll(@CurrentUser Long userId) {
        return CommonResponse.createSuccess(attendanceUseCase.findAll(userId));
    }
}
