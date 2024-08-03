package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserUseCase {

    void apply(UserDTO.SignUp dto);

    Map<Integer, List<UserDTO.Response>> findAll();
}
