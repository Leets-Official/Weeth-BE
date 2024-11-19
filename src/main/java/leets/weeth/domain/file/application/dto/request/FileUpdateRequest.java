package leets.weeth.domain.file.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FileUpdateRequest(
        @NotBlank Long fileId,
        String fileName,
        String fileUrl
) {
}
