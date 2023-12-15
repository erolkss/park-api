package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.config.entity.User;
import br.com.ero.demoparkapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    @Transactional
    public User editPassword(Long id, String password) {
        User user = getById(id);
        user.setPassword(password);
        return user;
    }
}
