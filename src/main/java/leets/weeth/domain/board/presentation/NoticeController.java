package leets.weeth.domain.board.presentation;


import static leets.weeth.domain.board.presentation.ResponseMessage.NOTICE_FIND_ALL_SUCCESS;
import static leets.weeth.domain.board.presentation.ResponseMessage.NOTICE_FIND_BY_ID_SUCCESS;

import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.usecase.NoticeUsecase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class NoticeController {

    private final NoticeUsecase noticeUsecase;

    @GetMapping
    public CommonResponse<List<NoticeDTO.ResponseAll>> findNotices(@RequestParam(required = false) Long noticeId, @RequestParam Integer count) {
        return CommonResponse.createSuccess(NOTICE_FIND_ALL_SUCCESS.getMessage(), noticeUsecase.findNotices(noticeId, count));
    }

    @GetMapping("/{noticeId}")
    public CommonResponse<NoticeDTO.Response> findNoticeById(@PathVariable Long noticeId) {
        return CommonResponse.createSuccess(NOTICE_FIND_BY_ID_SUCCESS.getMessage(), noticeUsecase.findNotice(noticeId));
    }
}
