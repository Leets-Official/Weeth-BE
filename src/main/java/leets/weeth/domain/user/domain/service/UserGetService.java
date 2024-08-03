package leets.weeth.domain.user.domain.service;

import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.enums.Status;
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

    public List<User> findAllByStatus(Status status) {
        return userRepository.findAllByStatusOrderByName(status);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean validateStudentId(String studentId) {
        return userRepository.existsByStudentId(studentId);
    }

    public boolean validateStudentId(String studentId, Long userId) {
        return userRepository.existsByStudentIdAndIdIsNot(studentId, userId);
    }

    public boolean validateTel(String tel) {
        return userRepository.existsByTel(tel);
    }

    public boolean validateTel(String tel, Long userId) {
        return userRepository.existsByTelAndIdIsNot(tel, userId);
    }
}
