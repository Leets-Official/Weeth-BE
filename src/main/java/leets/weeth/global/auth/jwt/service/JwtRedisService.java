package leets.weeth.global.auth.jwt.service;

import leets.weeth.domain.user.application.exception.RoleNotFoundException;
import leets.weeth.domain.user.domain.entity.enums.Role;
import leets.weeth.global.auth.jwt.exception.InvalidTokenException;
import leets.weeth.global.auth.jwt.exception.RedisTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtRedisService {

    @Value("${weeth.jwt.refresh.expiration}")
    private Long expirationTime;

    private static final String PREFIX = "refreshToken:";

    private final RedisTemplate<String, String> redisTemplate;

    public void set(String email, String refreshToken, Role role) {
        String key = getKey(email);
        redisTemplate.opsForHash().put(key, "token", refreshToken);
        redisTemplate.opsForHash().put(key, "role", role.toString());
        redisTemplate.opsForHash().put(key, "email", email);
        redisTemplate.expire(key, expirationTime, TimeUnit.MINUTES);
        log.info("Refresh Token 저장/업데이트: {}", key);
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

    public Role getRole(String email) {
        String key = getKey(email);
        String roleValue = (String) redisTemplate.opsForHash().get(key, "role");

        return Optional.ofNullable(roleValue)
                .map(Role::valueOf)
                .orElseThrow(RoleNotFoundException::new);
    }

    public void updateRole(String email, String role) {
        String key = getKey(email);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForHash().put(key, "role", role);
        }
    }

    private String find(String email) {
        String key = getKey(email);
        return Optional.ofNullable((String) redisTemplate.opsForHash().get(key, "token"))
                .orElseThrow(RedisTokenNotFoundException::new);
    }

    private String getKey(String email){
        return PREFIX + email;
    }
}
