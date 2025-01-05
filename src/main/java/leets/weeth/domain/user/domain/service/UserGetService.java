package leets.weeth.domain.user.domain.service;

import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.enums.Status;
import leets.weeth.domain.user.domain.repository.UserRepository;
import leets.weeth.domain.user.application.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserGetService {

    private final UserRepository userRepository;

    public User find(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User find(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public Optional<User> find(long kakaoId){
        return userRepository.findByKakaoId(kakaoId);
    }

    public Boolean check(String email) {
        return !userRepository.existsByEmail(email);
    }

    public List<User> findAllByStatus(Status status) {
        return userRepository.findAllByStatusOrderByName(status);
    }

    public List<User> findAllByCardinal(int cardinal) {
        return userRepository.findByCardinal(cardinal);
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
