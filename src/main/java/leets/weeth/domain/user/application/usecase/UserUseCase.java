package leets.weeth.domain.user.application.usecase;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.UserDTO.*;

public interface UserUseCase {

    void apply(SignUp dto);

    Map<Integer, List<Response>> findAll();

    Response find(Long userId);

    void update(Update dto, Long userId);
}
