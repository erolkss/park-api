package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.User;
import br.com.ero.demoparkapi.service.UserService;
import br.com.ero.demoparkapi.web.dto.UserCreateDto;
import br.com.ero.demoparkapi.web.dto.UserPasswordDto;
import br.com.ero.demoparkapi.web.dto.UserResponseDto;
import br.com.ero.demoparkapi.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        User user1 = userService.saveUser(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user1));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponseDto> getByIdUser(@PathVariable Long id) {
        User user1 = userService.getById(id);
        return ResponseEntity.ok(UserMapper.toDto(user1));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Void> UpdatePasswordUser(@PathVariable Long id, @Valid @RequestBody UserPasswordDto userPasswordDto) {
        User user1 = userService.editPassword(id, userPasswordDto.getCurrentPassword(), userPasswordDto.getNewPassword(), userPasswordDto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUser() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }



}
