package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.request.UserRequestDto;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.*;
import static leets.weeth.domain.user.application.dto.response.UserResponseDto.*;


public interface UserUseCase {

    SocialLoginResponse login(UserRequestDto.login dto);

    void apply(SignUp dto);

    void register(Register dto, Long userId);

    Response find(Long userId);

    Map<Integer, List<Response>> findAll();

    Map<Integer, List<SummaryResponse>> findAllUser();

    List<AdminResponse> findAllByAdmin();

    UserResponse findUserDetails(Long userId);

    void update(Update dto, Long userId);

    void accept(Long userId);

    void update(Long userId, String role);

    void leave(Long userId);

    void ban(Long userId);

    void applyOB(Long userId, Integer cardinal);

    void reset(Long userId);
}
