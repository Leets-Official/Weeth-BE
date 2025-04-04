package leets.weeth.domain.penalty.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.weeth.domain.penalty.application.dto.PenaltyDTO;
import leets.weeth.domain.penalty.application.usecase.PenaltyUsecase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static leets.weeth.domain.penalty.presentation.ResponseMessage.*;

@Tag(name = "PENALTY ADMIN", description = "[ADMIN] 패널티 어드민 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/penalties")
public class PenaltyAdminController {

    private final PenaltyUsecase penaltyUsecase;

    @PostMapping
    @Operation(summary="패널티 부여")
    public CommonResponse<String> assignPenalty(@RequestBody PenaltyDTO.Save dto){
        penaltyUsecase.save(dto);
        return CommonResponse.createSuccess(PENALTY_ASSIGN_SUCCESS.getMessage());
    }

    @PatchMapping
    @Operation(summary = "패널티 수정")
    public CommonResponse<String> update(@RequestBody PenaltyDTO.Update dto){
        penaltyUsecase.update(dto);
        return CommonResponse.createSuccess(PENALTY_UPDATE_SUCCESS.getMessage());
    }

    @GetMapping
    @Operation(summary="전체 패널티 조회")
    public CommonResponse<List<PenaltyDTO.Response>> findAll(){
        return CommonResponse.createSuccess(PENALTY_FIND_ALL_SUCCESS.getMessage(), penaltyUsecase.find());
    }

    @DeleteMapping
    @Operation(summary="패널티 삭제")
    public CommonResponse<String> delete(@RequestParam Long penaltyId){
        penaltyUsecase.delete(penaltyId);
        return CommonResponse.createSuccess(PENALTY_DELETE_SUCCESS.getMessage());
    }

}
