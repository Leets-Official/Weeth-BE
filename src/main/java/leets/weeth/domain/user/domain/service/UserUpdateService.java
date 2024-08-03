package leets.weeth.domain.user.domain.service;

import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static leets.weeth.domain.user.application.dto.UserDTO.Update;
import static leets.weeth.domain.user.domain.entity.enums.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public void update(Update dto, Long userId) {
        User update = mapper.update(dto, userId);
        userRepository.save(update);
    }

    public void accept(Long userId) {
        User update = mapper.update(userId, ACTIVE);
        userRepository.save(update);
    }
}
