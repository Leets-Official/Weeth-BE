package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.UserDTO;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.UserDTO.*;

public interface UserUseCase {

    void apply(SignUp dto);

    Map<Integer, List<Response>> findAll();

    Response find(Long userId);
}
