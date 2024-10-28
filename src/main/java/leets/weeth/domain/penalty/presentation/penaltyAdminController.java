package leets.weeth.domain.penalty.presentation;

import static leets.weeth.domain.penalty.presentation.ResponseMessage.PENALTY_ASSIGN_SUCCESS;
import static leets.weeth.domain.penalty.presentation.ResponseMessage.PENALTY_DELETE_SUCCESS;
import static leets.weeth.domain.penalty.presentation.ResponseMessage.PENALTY_FIND_ALL_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.penalty.application.dto.PenaltyDTO;
import leets.weeth.domain.penalty.application.usecase.PenaltyUsecase;

import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "penaltyAdminController", description = "패널티 관련 어드민 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/penalties")
public class penaltyAdminController {

    private final PenaltyUsecase penaltyUsecase;

    @PostMapping
    @Operation(summary="전체 패널티 조회")
    public CommonResponse<String> assignPenalty(@RequestBody PenaltyDTO.Save dto){
        penaltyUsecase.save(dto);
        return CommonResponse.createSuccess(PENALTY_ASSIGN_SUCCESS.getMessage());
    }

    @GetMapping
    @Operation(summary="패널티 부여")
    public CommonResponse<List<PenaltyDTO.Response>> findAll(){
        return CommonResponse.createSuccess(PENALTY_FIND_ALL_SUCCESS.getMessage(),penaltyUsecase.find());
    }

    @DeleteMapping
    @Operation(summary="패널티 삭제")
    public CommonResponse<String> delete(@RequestParam Long penaltyId){
        penaltyUsecase.delete(penaltyId);
        return CommonResponse.createSuccess(PENALTY_DELETE_SUCCESS.getMessage());
    }

}
