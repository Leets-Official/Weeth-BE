package leets.weeth.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leets.weeth.domain.user.application.dto.request.CardinalSaveRequest;
import leets.weeth.domain.user.application.dto.response.CardinalResponse;
import leets.weeth.domain.user.application.usecase.CardinalUseCase;
import leets.weeth.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static leets.weeth.domain.user.presentation.ResponseMessage.CARDINAL_FIND_ALL_SUCCESS;
import static leets.weeth.domain.user.presentation.ResponseMessage.CARDINAL_SAVE_SUCCESS;

@Tag(name = "CARDINAL")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/cardinals")
public class CardinalController {

    private final CardinalUseCase cardinalUseCase;

    @GetMapping
    @Operation(summary = "현재 저장된 기수 목록 조회 API")
    public CommonResponse<List<CardinalResponse>> findAllCardinals() {
        List<CardinalResponse> response = cardinalUseCase.findAll();

        return CommonResponse.createSuccess(CARDINAL_FIND_ALL_SUCCESS.getMessage(), response);
    }

    @PostMapping
    @Operation(summary = "새로운 기수 정보 저장 API")
    public CommonResponse<Void> save(@RequestBody @Valid CardinalSaveRequest dto) {
        cardinalUseCase.save(dto);

        return CommonResponse.createSuccess(CARDINAL_SAVE_SUCCESS.getMessage());
    }

}
