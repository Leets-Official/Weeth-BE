package leets.weeth.domain.board.presentation;

import jakarta.validation.Valid;
import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.board.application.usecase.NoticeUsecaseImpl;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class NoticeController {

    private final NoticeUsecaseImpl noticeUsecase;

    @PostMapping
    public CommonResponse<String> save(@RequestBody @Valid NoticeDTO.Save dto, @CurrentUser Long userId) {
        noticeUsecase.save(dto, userId);
        return CommonResponse.createSuccess("공지사항 생성 성공");
    }
}
