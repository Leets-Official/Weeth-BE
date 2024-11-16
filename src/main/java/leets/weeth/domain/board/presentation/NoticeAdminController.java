package leets.weeth.domain.board.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.usecase.NoticeUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static leets.weeth.domain.board.presentation.ResponseMessage.*;

@Tag(name = "NoticeAdminController", description = "공지사항 관련 어드민 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/notices")
public class NoticeAdminController {

    private final NoticeUsecase noticeUsecase;

    @PostMapping
    @Operation(summary="공지사항 생성")
    public CommonResponse<String> save(@RequestBody @Valid NoticeDTO.Save dto,
                                       @Parameter(hidden = true) @CurrentUser Long userId) {
        noticeUsecase.save(dto, userId);
        return CommonResponse.createSuccess(NOTICE_CREATED_SUCCESS.getMessage());
    }

    @PatchMapping(value = "/{noticeId}")
    @Operation(summary="특정 공지사항 수정")
    public CommonResponse<String> update(@PathVariable Long noticeId,
                                         @RequestBody @Valid NoticeDTO.Update dto,
                                         @Parameter(hidden = true) @CurrentUser Long userId) throws UserNotMatchException {
        noticeUsecase.update(noticeId, dto, userId);
        return CommonResponse.createSuccess(NOTICE_UPDATED_SUCCESS.getMessage());
    }

    @DeleteMapping("/{noticeId}")
    @Operation(summary="특정 공지사항 삭제")
    public CommonResponse<String> delete(@PathVariable Long noticeId, @Parameter(hidden = true) @CurrentUser Long userId) throws UserNotMatchException {
        noticeUsecase.delete(noticeId, userId);
        return CommonResponse.createSuccess(NOTICE_DELETED_SUCCESS.getMessage());
    }

}
