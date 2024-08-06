package leets.weeth.domain.board.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.usecase.NoticeUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/notices")
public class NoticeAdminController {

    private final NoticeUsecase noticeUsecase;

    @PostMapping
    public CommonResponse<String> save(@RequestPart @Valid NoticeDTO.Save dto,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                       @Parameter(hidden = true) @CurrentUser Long userId) {
        noticeUsecase.save(dto, files, userId);
        return CommonResponse.createSuccess("공지사항 생성 성공");
    }

    @PatchMapping("/{noticeId}")
    public CommonResponse<String> update(@PathVariable Long noticeId,
                                         @RequestPart @Valid NoticeDTO.Update dto,
                                         @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                         @CurrentUser Long userId) throws UserNotMatchException {
        noticeUsecase.update(noticeId, dto, files, userId);
        return CommonResponse.createSuccess("공지사항 수정 성공");
    }

    @DeleteMapping("/{noticeId}")
    public CommonResponse<String> delete(@PathVariable Long noticeId, @CurrentUser Long userId) throws UserNotMatchException {
        noticeUsecase.delete(noticeId, userId);
        return CommonResponse.createSuccess("공지사항 삭제 성공");
    }

}
