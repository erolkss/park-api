package br.com.ero.demoparkapi.repository;


import br.com.ero.demoparkapi.config.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);
    @Query("select u.role from users u where u.username like :username")
    User.Role findRoleByUsername(String username);
}
