package leets.weeth.global.auth.jwt.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;
import leets.weeth.global.auth.jwt.service.JwtRedisService;
import leets.weeth.global.auth.jwt.service.JwtProvider;
import leets.weeth.global.auth.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtManageUseCase {

    private final JwtProvider jwtProvider;
    private final JwtService jwtService;
    private final JwtRedisService jwtRedisService;

    // 토큰 발급
    public JwtDto create(Long id, String email){
        String accessToken = jwtProvider.createAccessToken(id, email);
        String refreshToken = jwtProvider.createRefreshToken(id);

        updateToken(email, refreshToken);

        return new JwtDto(accessToken, refreshToken);
    }

    // 토큰 헤더로 전송
    public void sendToken(JwtDto dto, HttpServletResponse response) throws IOException {
        jwtService.sendAccessAndRefreshToken(response, dto.accessToken(), dto.refreshToken());
    }

    // 토큰 재발급
    public JwtDto reIssueToken(String email, HttpServletRequest request){
        String requestToken = jwtService.extractRefreshToken(request);
        jwtProvider.validate(requestToken);

        Long userId = jwtService.extractId(requestToken).get();

        jwtRedisService.validateRefreshToken(email, requestToken);

        JwtDto token = create(userId, email);
        jwtRedisService.set(email, token.refreshToken());

        return token;
    }

    // 리프레시 토큰 업데이트
    private void updateToken(String email, String refreshToken){
        jwtRedisService.set(email, refreshToken);
    }

}
