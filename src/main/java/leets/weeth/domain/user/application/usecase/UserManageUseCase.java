package leets.weeth.domain.user.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.refreshRequest;

public interface UserManageUseCase {
    JwtDto refresh(HttpServletRequest request);
}
