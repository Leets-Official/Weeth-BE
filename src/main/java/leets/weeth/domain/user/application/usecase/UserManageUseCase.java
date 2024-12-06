package leets.weeth.domain.user.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import leets.weeth.domain.user.application.dto.request.UserRequestDto;
import leets.weeth.domain.user.application.dto.response.UserResponseDto;
import leets.weeth.global.auth.jwt.application.dto.JwtDto;

import java.util.List;
import java.util.Map;

import static leets.weeth.domain.user.application.dto.request.UserRequestDto.refreshRequest;

public interface UserManageUseCase {

    UserResponseDto.Response find(Long userId);

    Map<Integer, List<UserResponseDto.Response>> findAll();

    Map<Integer, List<UserResponseDto.SummaryResponse>> findAllUser();

    List<UserResponseDto.AdminResponse> findAllByAdmin();

    UserResponseDto.UserResponse findUserDetails(Long userId);

    void update(UserRequestDto.Update dto, Long userId);

    void accept(Long userId);

    void update(Long userId, String role);

    void leave(Long userId);

    void ban(Long userId);

    void applyOB(Long userId, Integer cardinal);

    void reset(Long userId);
}
