package leets.weeth.domain.user.domain.service;

import jakarta.transaction.Transactional;
import leets.weeth.domain.user.application.dto.request.UserRequestDto;
import leets.weeth.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.*;


@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdateService {

    public void update(User user, Update dto, PasswordEncoder passwordEncoder) {
        user.update(dto, passwordEncoder);
    }
    public void update(User user, Register dto) {
        user.update(dto);
    }
    public void accept(User user) {
        user.accept();
    }

    public void update(User user, String role) {
        user.update(role);
    }

    public void applyOB(User user, Integer cardinal) {
        user.applyOB(cardinal);
    }

    public void reset(User user, PasswordEncoder passwordEncoder) {
        user.reset(passwordEncoder);
    }
}
