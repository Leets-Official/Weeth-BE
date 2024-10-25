package leets.weeth.global.auth.jwt.service;

import leets.weeth.global.auth.jwt.exception.InvalidTokenException;
import leets.weeth.global.auth.jwt.exception.RedisTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtRedisService {

    @Value("${weeth.jwt.refresh.expiration}")
    private Long expirationTime;

    private static final String PREFIX = "refreshToken:";

    private final RedisTemplate<String, String> redisTemplate;

    public void set(String email, String refreshToken) {
        String key = getKey(email);
        redisTemplate.opsForValue().set(key, refreshToken, expirationTime, TimeUnit.MILLISECONDS);
    }

    public void delete(String email) {
        String key = getKey(email);
        redisTemplate.delete(key);
    }

    public void validateRefreshToken(String email, String requestToken) {
        if (!find(email).equals(requestToken)) {
            throw new InvalidTokenException();
        }
    }

    private String find(String email) {
        String key = getKey(email);
        return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .orElseThrow(RedisTokenNotFoundException::new);
    }

    private String getKey(String email){
        return PREFIX + email;
    }

}
