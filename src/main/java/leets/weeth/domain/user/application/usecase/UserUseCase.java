package leets.weeth.domain.user.application.usecase;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.UserDTO.*;

public interface UserUseCase {

    void apply(SignUp dto);

    Response find(Long userId);

    Map<Integer, List<Response>> findAll();

    List<AdminResponse> findAllByAdmin();

    void update(Update dto, Long userId);

    void accept(Long userId);

    void update(Long userId, String role);

    void leave(Long userId);

    void ban(Long userId);

    void applyOB(Long userId, Integer cardinal);

    void reset(Long userId);
}
