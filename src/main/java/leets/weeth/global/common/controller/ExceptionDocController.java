package leets.weeth.global.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.attendance.application.exception.AttendanceErrorCode;
import leets.weeth.domain.schedule.application.exception.EventErrorCode;
import leets.weeth.domain.schedule.application.exception.MeetingErrorCode;
import leets.weeth.domain.user.application.exception.UserErrorCode;
import leets.weeth.global.auth.jwt.exception.JwtErrorCode;
import leets.weeth.global.common.exception.ApiErrorCodeExample;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exceptions")
@Tag(name = "Exception Document", description = "API 에러 코드 문서")
public class ExceptionDocController {

    @GetMapping("/user")
    @Operation(summary = "User 도메인 에러 코드 목록")
    @ApiErrorCodeExample(UserErrorCode.class)
    public void userErrorCodes() {
    }

    //todo: SAS 관련 예외도 추가
    @GetMapping("/auth")
    @Operation(summary = "인증/인가 에러 코드 목록")
    @ApiErrorCodeExample({JwtErrorCode.class})
    public void authErrorCodes() {
    }

    @GetMapping("/Schedule")
    @Operation(summary = "Schedule 도메인 에러 코드 목록")
    @ApiErrorCodeExample({EventErrorCode.class, MeetingErrorCode.class})
    public void scheduleErrorCodes() {
    }

    @GetMapping("/Attendance")
    @Operation(summary = "Attendance 도메인 에러 코드 목록")
    @ApiErrorCodeExample(AttendanceErrorCode.class)
    public void attendanceErrorCodes() {
    }
}
