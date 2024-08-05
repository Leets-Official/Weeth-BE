package leets.weeth.domain.penalty.presentation;

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
        return CommonResponse.createSuccess("패널티 부여 성공");
    }

    @GetMapping
    public CommonResponse<List<PenaltyDTO.Response>> findAll(){
        return CommonResponse.createSuccess(penaltyUsecase.find());
    }

    @DeleteMapping
    public CommonResponse<String> delete(Long penaltyId){
        penaltyUsecase.delete(penaltyId);
        return CommonResponse.createSuccess("패널티 삭제 성공");
    }

}
