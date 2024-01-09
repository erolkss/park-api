package br.com.ero.demoparkapi.repository;

import br.com.ero.demoparkapi.config.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
