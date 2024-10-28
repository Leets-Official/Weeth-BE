package leets.weeth.domain.user.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;
import leets.weeth.global.auth.jwt.application.usecase.JwtManageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.refreshRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManageUseCaseImpl implements UserManageUseCase {
    private final JwtManageUseCase jwtManageUseCase;

    @Override
    @Transactional
    public JwtDto refresh(refreshRequest dto, HttpServletRequest request) {

        JwtDto token = jwtManageUseCase.reIssueToken(dto.email(), request);

        log.info("RefreshToken 발급 완료: {}", dto.email());
        return new JwtDto(token.accessToken(), token.refreshToken());
    }

}
