package leets.weeth.domain.attendance.presentation;

import leets.weeth.domain.attendance.application.usecase.AttendanceUseCase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    private final AttendanceUseCase attendanceUseCase;

    @PatchMapping
    public CommonResponse<Void> checkIn(@CurrentUser Long userId) {
        attendanceUseCase.checkIn(userId);
        return CommonResponse.createSuccess();
    }
}
