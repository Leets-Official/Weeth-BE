package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.enums.Role;
import leets.weeth.domain.user.domain.service.UserDeleteService;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.domain.user.domain.service.UserSaveService;
import leets.weeth.domain.user.domain.service.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static leets.weeth.domain.user.application.dto.UserDTO.*;
import static leets.weeth.domain.user.domain.entity.enums.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserGetService userGetService;
    private final UserSaveService userSaveService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserUpdateService userUpdateService;
    private final UserDeleteService userDeleteService;

    @Override
    public void apply(SignUp dto) {
        userSaveService.save(mapper.from(dto, passwordEncoder));
    }

    @Override
    public Map<Integer, List<Response>> findAll() {
        return userGetService.findAllByStatus(ACTIVE).stream()
                .flatMap(user -> Stream.concat(
                        user.getCardinals().stream()
                                .map(cardinal -> new AbstractMap.SimpleEntry<>(cardinal, mapper.to(user))), // 기수별 Map
                        Stream.of(new AbstractMap.SimpleEntry<>(0, mapper.to(user)))    // 모든 기수는 cardinal 0에 저장
                ))
                .collect(Collectors.groupingBy(Map.Entry::getKey,   // key = 기수, value = 유저 정보
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    @Override
    public List<AdminResponse> findAllByAdmin() {
        return userGetService.findAll().stream()
                .map(mapper::toAdminResponse)
                .toList();
    }

    @Override
    public Response find(Long userId) {
        return mapper.to(userGetService.find(userId));
    }

    @Override
    public void update(Update dto, Long userId) {
        User user = userGetService.find(userId);
        userUpdateService.update(user, dto);
    }

    @Override
    public void accept(Long userId) {
        User user = userGetService.find(userId);
        userUpdateService.accept(user);
    }

    @Override
    public void update(Long userId, String role) {
        User user = userGetService.find(userId);
        userUpdateService.update(user, role);
    }

    @Override
    public void leave(Long userId) {
        User user = userGetService.find(userId);
        userDeleteService.leave(user);
    }

    @Override
    public void ban(Long userId) {
        User user = userGetService.find(userId);
        userDeleteService.ban(user);
    }
}
