package leets.weeth.domain.user.application.usecase;

import leets.weeth.domain.user.application.dto.UserDTO;

public interface UserUseCase {

    void apply(UserDTO.SignUp dto);
}
