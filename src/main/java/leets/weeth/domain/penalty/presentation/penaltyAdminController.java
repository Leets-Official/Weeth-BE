package leets.weeth.domain.penalty.presentation;

import static leets.weeth.domain.penalty.presentation.ResponseMessage.PENALTY_ASSIGN_SUCCESS;
import static leets.weeth.domain.penalty.presentation.ResponseMessage.PENALTY_DELETE_SUCCESS;
import static leets.weeth.domain.penalty.presentation.ResponseMessage.PENALTY_FIND_ALL_SUCCESS;

import leets.weeth.domain.penalty.application.dto.PenaltyDTO;
import leets.weeth.domain.penalty.application.usecase.PenaltyUsecase;

import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/penalties")
public class penaltyAdminController {

    private final PenaltyUsecase penaltyUsecase;

    @PostMapping
    public CommonResponse<String> assignPenalty(@RequestBody PenaltyDTO.Save dto){
        penaltyUsecase.save(dto);
        return CommonResponse.createSuccess(PENALTY_ASSIGN_SUCCESS.getStatusCode(),
                PENALTY_ASSIGN_SUCCESS.getMessage());
    }

    @GetMapping
    public CommonResponse<List<PenaltyDTO.Response>> findAll(){
        return CommonResponse.createSuccess(PENALTY_FIND_ALL_SUCCESS.getStatusCode(),
                PENALTY_FIND_ALL_SUCCESS.getMessage(),penaltyUsecase.find());
    }

    @DeleteMapping
    public CommonResponse<String> delete(@RequestParam Long penaltyId){
        penaltyUsecase.delete(penaltyId);
        return CommonResponse.createSuccess(PENALTY_DELETE_SUCCESS.getStatusCode(),
                PENALTY_DELETE_SUCCESS.getMessage());
    }

}
