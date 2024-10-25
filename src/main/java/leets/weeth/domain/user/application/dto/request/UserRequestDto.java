package leets.weeth.domain.user.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequestDto {

    public record SignUp (
            @NotBlank String name,
            @Email @NotBlank String email,
            @NotBlank        String password,
            @NotBlank        String studentId,
            @NotBlank        String tel,
            @NotNull String position,
            @NotNull         String department,
            @NotNull         Integer cardinal
    ) {}

    public record Update (
            @NotBlank        String name,
            @Email @NotBlank String email,
            @NotBlank        String password,
            @NotBlank        String studentId,
            @NotBlank        String tel,
            @NotNull         String department
    ) {}

    public record refreshRequest(
            @Email String email
    ){}

}
