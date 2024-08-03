package leets.weeth.domain.user.domain.service;

import jakarta.transaction.Transactional;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeleteService {

    private final UserRepository userRepository;

    @Transactional
    public void leave(User user) {
        user.leave();
    }

    @Transactional
    public void ban(User user) {
        user.ban();
    }
}
