package leets.weeth.domain.user.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import leets.weeth.domain.user.application.dto.request.UserRequestDto;
import leets.weeth.domain.user.application.dto.response.UserResponseDto;
import leets.weeth.global.auth.jwt.service.JwtRedisService;
import leets.weeth.global.auth.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManageUseCaseImpl implements UserManageUseCase {
    private final JwtService jwtService;
    private final JwtRedisService jwtRedisService;

    @Override
    @Transactional
    public UserResponseDto.refreshResponse refresh(UserRequestDto.refreshRequest dto, HttpServletRequest request) {

        String refreshToken = jwtService.extractRefreshToken(request);
        jwtService.validate(refreshToken);
        Long userId = jwtService.extractId(refreshToken).get();
        String email = dto.email();

        // redis에 저장된 토큰과 비교
        jwtRedisService.validateRefreshToken(email, refreshToken);

        String newAccessToken = jwtService.createAccessToken(userId, email);
        String newRefreshToken = jwtService.createRefreshToken(userId);

        jwtRedisService.set(email, newRefreshToken);

        log.info("RefreshToken 발급 완료: {}", email);
        return new UserResponseDto.refreshResponse(newAccessToken, newRefreshToken);
    }

}
