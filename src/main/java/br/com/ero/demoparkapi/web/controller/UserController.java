package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.User;
import br.com.ero.demoparkapi.service.UserService;
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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getByIdUser(@PathVariable Long id) {
        User user1 = userService.getById(id);
        return ResponseEntity.ok(user1);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<User> UpdatePasswordUser(@PathVariable Long id, @RequestBody User user) {
        User user1 = userService.editPassword(id, user.getPassword());
        return ResponseEntity.ok(user1);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }



}
