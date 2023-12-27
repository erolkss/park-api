package br.com.ero.demoparkapi.repository;

import br.com.ero.demoparkapi.config.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    @Query("SELECT u.role FROM USERS u WHERE u.name LIKE :name")
    User.Role findRoleByUserName(String username);
}
