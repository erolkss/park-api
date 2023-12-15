package br.com.ero.demoparkapi.repository;

import br.com.ero.demoparkapi.config.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
