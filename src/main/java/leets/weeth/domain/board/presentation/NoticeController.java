package leets.weeth.domain.board.presentation;


import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.usecase.NoticeUsecase;
import leets.weeth.global.common.error.exception.custom.LastNoticeFoundException;
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
    public CommonResponse<List<NoticeDTO.ResponseAll>> findNotices(@RequestParam(required = false) Long noticeId, @RequestParam Integer count) throws LastNoticeFoundException {
        return CommonResponse.createSuccess(noticeUsecase.findNotices(noticeId, count));
    }

    @GetMapping("/{noticeId}")
    public CommonResponse<NoticeDTO.Response> findNoticeById(@PathVariable Long noticeId) {
        return CommonResponse.createSuccess(noticeUsecase.findNotice(noticeId));
    }
}
