package leets.weeth.domain.user.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequestDto {

    public record Login(
            @NotBlank String authCode
    ) {
    }

    public record SignUp(
            @NotBlank String name,
            @Email @NotBlank String email,
            @NotBlank String password,
            @NotBlank String studentId,
            @NotBlank String tel,
            @NotNull String position,
            @NotNull String department,
            @NotNull Integer cardinal
    ) {
    }

    public record Register(
            @NotNull Long kakaoId,
            @NotBlank String name,
            @NotBlank String studentId,
            @NotBlank String email,
            @NotNull String department,
            @NotBlank String tel,
            @NotNull Integer cardinal,
            @NotNull String position
    ) {
    }

    public record Update(
            @NotBlank String name,
            @Email @NotBlank String email,
            @NotBlank String studentId,
            @NotBlank String tel,
            @NotNull String department
    ) {
    }

    public record NormalLogin(
            @Email @NotBlank String email,
            @NotBlank String passWord,
            @NotNull Long kakaoId
    ) {
    }

}
