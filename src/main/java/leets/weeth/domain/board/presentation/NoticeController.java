package leets.weeth.domain.board.presentation;


import static leets.weeth.domain.board.presentation.ResponseMessage.NOTICE_FIND_ALL_SUCCESS;
import static leets.weeth.domain.board.presentation.ResponseMessage.NOTICE_FIND_BY_ID_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.usecase.NoticeUsecase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;


@Tag(name = "NoticeController", description = "공지사항 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class NoticeController {

    private final NoticeUsecase noticeUsecase;

    @GetMapping
    @Operation(summary="최근 공지사항 조회 및 입력된 개수 만큼 조회")
    public CommonResponse<Slice<NoticeDTO.ResponseAll>> findNotices(@RequestParam("pageNumber") int pageNumber,
                                                                   @RequestParam("pageSize") int pageSize) {
        return CommonResponse.createSuccess(NOTICE_FIND_ALL_SUCCESS.getMessage(), noticeUsecase.findNotices(pageNumber, pageSize));
    }

    @GetMapping("/{noticeId}")
    @Operation(summary="특정 공지사항 조회")
    public CommonResponse<NoticeDTO.Response> findNoticeById(@PathVariable Long noticeId) {
        return CommonResponse.createSuccess(NOTICE_FIND_BY_ID_SUCCESS.getMessage(), noticeUsecase.findNotice(noticeId));
    }
}
