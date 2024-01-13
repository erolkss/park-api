package br.com.ero.demoparkapi.repository;

import br.com.ero.demoparkapi.config.entity.Client;
import br.com.ero.demoparkapi.repository.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c")
    Page<ClientProjection> findAllPageable(Pageable pageable);


    Client findByUserId(Long id);
}
