package leets.weeth.global.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import leets.weeth.domain.user.domain.entity.enums.Role;
import leets.weeth.global.auth.jwt.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class JwtProvider {

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String ID_CLAIM = "id";
    private static final String ROLE_CLAIM = "role";

    @Value("${weeth.jwt.key}")
    private String key;
    @Value("${weeth.jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;
    @Value("${weeth.jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    public String createAccessToken(Long id, String email, Role role) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(ID_CLAIM, id)
                .withClaim(EMAIL_CLAIM, email)
                .withClaim(ROLE_CLAIM, role.toString())
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

    public boolean validate(String token) {
        try {
            JWT.require(Algorithm.HMAC512(key)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            throw new InvalidTokenException();
        }
    }

}
