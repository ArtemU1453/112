package by.mchs.e112.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record UserCreateRequest(
    @NotBlank @Size(min = 3, max = 64) String username,
    @NotBlank @Size(max = 100) String firstName,
    @NotBlank @Size(max = 100) String lastName,
    @NotBlank @Email @Size(max = 255) String email,
    @Pattern(regexp = "^\\+375\\d{9}$", message = "Телефон в формате +375XXXXXXXXX") String phone,
    @Size(max = 20) String employeeNumber,
    @Size(max = 200) String department,
    @NotBlank @Size(min = 8, max = 128) String password,
    @NotEmpty Set<String> roles
) {
}
