package leets.weeth.global.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import leets.weeth.global.auth.jwt.exception.InvalidTokenException;
import leets.weeth.global.auth.jwt.exception.TokenNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String ID_CLAIM = "id";
    private static final String BEARER = "Bearer ";

    @Value("${weeth.jwt.key}")
    private String key;
    @Value("${weeth.jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;
    @Value("${weeth.jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;
    @Value("${weeth.jwt.access.header}")
    private String accessHeader;
    @Value("${weeth.jwt.refresh.header}")
    private String refreshHeader;

    public String createAccessToken(Long id, String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(ID_CLAIM, id)
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(key));
    }

    public String createRefreshToken(Long id) {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .withClaim(ID_CLAIM, id)
                .sign(Algorithm.HMAC512(key));
    }

    public String extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""))
                .orElseThrow(TokenNotFoundException::new);
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(key))
                    .build()
                    .verify(accessToken)
                    .getClaim(EMAIL_CLAIM)
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public Optional<Long> extractId(String token) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(key))
                    .build()
                    .verify(token)
                    .getClaim(ID_CLAIM)
                    .asLong());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public boolean validate(String token) {
        try {
            JWT.require(Algorithm.HMAC512(key)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            throw new InvalidTokenException();
        }
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);

        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }


}
