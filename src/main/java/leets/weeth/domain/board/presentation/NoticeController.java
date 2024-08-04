package leets.weeth.domain.board.presentation;


import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.usecase.NoticeUsecase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class NoticeController {

    private final NoticeUsecase noticeUsecase;

    @GetMapping
    public CommonResponse<List<NoticeDTO.Response>> findNotices() {
        return CommonResponse.createSuccess(noticeUsecase.findNotices());
    }

    @GetMapping("/{noticeId}")
    public CommonResponse<NoticeDTO.Response> findNoticeById(@PathVariable Long noticeId) {
        return CommonResponse.createSuccess(noticeUsecase.findNotice(noticeId));
    }
}
