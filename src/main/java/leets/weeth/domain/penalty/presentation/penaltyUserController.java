package leets.weeth.domain.penalty.presentation;

import static leets.weeth.domain.penalty.presentation.ResponseMessage.PENALTY_USER_FIND_SUCCESS;

import leets.weeth.domain.penalty.application.dto.PenaltyDTO;
import leets.weeth.domain.penalty.application.usecase.PenaltyUsecase;
import leets.weeth.global.auth.annotation.CurrentUser;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/penalties")
public class penaltyUserController {

    private final PenaltyUsecase penaltyUsecase;

    @GetMapping
    public CommonResponse<PenaltyDTO.Response> findAllPenalties(@CurrentUser Long userId) {
        PenaltyDTO.Response penalties = penaltyUsecase.find(userId);
        return CommonResponse.createSuccess(PENALTY_USER_FIND_SUCCESS.getStatusCode(),
                PENALTY_USER_FIND_SUCCESS.getMessage(),penalties);
    }

}
