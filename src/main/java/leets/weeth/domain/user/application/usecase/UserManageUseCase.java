package leets.weeth.domain.user.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import leets.weeth.domain.user.application.dto.request.UserRequestDto;
import leets.weeth.domain.user.application.dto.response.UserResponseDto;

public interface UserManageUseCase {
    UserResponseDto.refreshResponse refresh(UserRequestDto.refreshRequest dto, HttpServletRequest request);
}
