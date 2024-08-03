package leets.weeth.domain.board.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.usecase.NoticeUsecaseImpl;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/notices")
public class NoticeAdminController {

    private final NoticeUsecaseImpl noticeUsecase;

    @PostMapping
    public CommonResponse<String> save(@RequestBody @Valid NoticeDTO.Save dto, @Parameter(hidden = true) @CurrentUser Long userId) {
        noticeUsecase.save(dto, userId);
        return CommonResponse.createSuccess("공지사항 생성 성공");
    }

    @PatchMapping("/{noticeId}")
    public CommonResponse<String> update(@PathVariable Long noticeId, @RequestBody @Valid NoticeDTO.Update dto, @CurrentUser Long userId) throws UserNotMatchException {
        noticeUsecase.update(noticeId, dto, userId);
        return CommonResponse.createSuccess("공지사항 수정 성공");
    }

    @DeleteMapping("/{noticeId}")
    public CommonResponse<String> delete(@PathVariable Long noticeId, @CurrentUser Long userId) throws UserNotMatchException {
        noticeUsecase.delete(noticeId, userId);
        return CommonResponse.createSuccess("공지사항 삭제 성공");
    }

}
