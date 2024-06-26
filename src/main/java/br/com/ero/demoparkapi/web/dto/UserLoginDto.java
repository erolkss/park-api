package br.com.ero.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDto {

    @NotBlank
    @Email(regexp = "^[a-z0-9.+-]+@[a-z--9.-]+\\.[a-z]{2,}$", message = "Invalid email format")
    private String username;
    @NotBlank
    @Size(min = 8, max = 30)
    private String password;

}
