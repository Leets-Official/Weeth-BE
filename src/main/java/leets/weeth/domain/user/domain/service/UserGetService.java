package leets.weeth.domain.user.domain.service;

import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.repository.UserRepository;
import leets.weeth.global.common.error.exception.custom.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static leets.weeth.domain.user.domain.entity.enums.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserGetService {

    private final UserRepository userRepository;

    public User find(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public Boolean check(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAllByStatusOrderByName(ACTIVE);
    }
}
