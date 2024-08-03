package leets.weeth.domain.user.domain.service;

import jakarta.transaction.Transactional;
import leets.weeth.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.user.application.dto.UserDTO.Update;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    @Transactional
    public void update(User user, Update dto, PasswordEncoder passwordEncoder) {
        user.update(dto, passwordEncoder);
    }

    @Transactional
    public void accept(User user) {
        user.accept();
    }

    @Transactional
    public void update(User user, String role) {
        user.update(role);
    }

    @Transactional
    public void applyOB(User user, Integer cardinal) {
        user.applyOB(cardinal);
    }

    @Transactional
    public void reset(User user, PasswordEncoder passwordEncoder) {
        user.reset(passwordEncoder);
    }
}
