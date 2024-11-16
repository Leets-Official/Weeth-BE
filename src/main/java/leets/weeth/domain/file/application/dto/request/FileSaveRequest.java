package leets.weeth.domain.file.application.dto.request;

public record FileSaveRequest(
        String fileName,
        String fileUrl
) {
}
