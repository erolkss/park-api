package br.com.ero.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDto {
    @NotBlank
    @Size(min = 8, max = 30)
    private String currentPassword;
    @NotBlank
    @Size(min = 8, max = 30)
    private String newPassword;
    @NotBlank
    @Size(min = 8, max = 30)
    private String confirmPassword;
}
