package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.UserDTO;
import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.domain.user.domain.service.UserSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserGetService userGetService;
    private final UserSaveService userSaveService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void apply(UserDTO.SignUp dto) {
        userSaveService.save(mapper.from(dto, passwordEncoder));
    }

    @Override
    public Map<Integer, List<UserDTO.Response>> findAll() {
        return userGetService.findAll().stream()
                .flatMap(user -> Stream.concat(
                        user.getCardinals().stream()
                                .map(cardinal -> new AbstractMap.SimpleEntry<>(cardinal, mapper.to(user))), // 기수별 Map
                        Stream.of(new AbstractMap.SimpleEntry<>(0, mapper.to(user)))    // 모든 기수는 cardinal 0에 저장
                ))
                .collect(Collectors.groupingBy(Map.Entry::getKey,   // key = 기수, value = 유저 정보
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }
}
