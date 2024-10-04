package leets.weeth.global.common.error.exception;

import lombok.Builder;

@Builder
public record BindExceptionResponse(
        String message,
        Object value
) {
}
