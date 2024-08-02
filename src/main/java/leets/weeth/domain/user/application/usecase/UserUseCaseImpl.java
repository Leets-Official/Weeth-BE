package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.UserDTO;
import leets.weeth.domain.user.application.mapper.UserMapper;
import leets.weeth.domain.user.domain.service.UserSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserSaveService userSaveService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void apply(UserDTO.SignUp dto) {
        userSaveService.save(mapper.from(dto, passwordEncoder));
    }
}
