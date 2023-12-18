package br.com.ero.demoparkapi.web.dto;


import jakarta.validation.constraints.Email;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserCreateDto {
    @Email(regexp = "", message = "Invalid email format")
    private String username;
    private String password;


}
