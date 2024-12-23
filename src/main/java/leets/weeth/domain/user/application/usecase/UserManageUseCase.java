package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.response.UserResponseDto;

import java.util.List;

public interface UserManageUseCase {


    List<UserResponseDto.AdminResponse> findAllByAdmin();

    void accept(Long userId);

    void update(Long userId, String role);

    void leave(Long userId);

    void ban(Long userId);

    void applyOB(Long userId, Integer cardinal);

    void reset(Long userId);
}
