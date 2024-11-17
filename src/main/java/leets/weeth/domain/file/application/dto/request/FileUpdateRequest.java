package leets.weeth.domain.file.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record FileUpdateRequest(
        @NotBlank Long fileId,
        @NotBlank String fileName,
        @URL String fileUrl
) {
}
