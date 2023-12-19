package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.config.entity.User;
import br.com.ero.demoparkapi.exception.UserNameUniqueViolationException;
import br.com.ero.demoparkapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        }catch (DataIntegrityViolationException ex){
            throw new UserNameUniqueViolationException(String.format("UserName {%s} already registered",user.getUsername()));
        }

    }


    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {

        if (!newPassword.equals(confirmPassword)){
            throw new RuntimeException("New Password and Confirm Password are different");
        }
        User user = getById(id);

        if (!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Your password does not match");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
