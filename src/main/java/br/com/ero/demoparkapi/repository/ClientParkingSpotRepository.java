package br.com.ero.demoparkapi.repository;

import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import br.com.ero.demoparkapi.jwt.JwtUserDetails;
import br.com.ero.demoparkapi.repository.projection.ClientParkingSpotProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientParkingSpotRepository extends JpaRepository<ClientParkingSpot, Long> {
    Optional<ClientParkingSpot> findByReceiptAndExitDateIsNull(String receipt);

    long countByClientCpfAndExitDateIsNotNull(String cpf);

    Page<ClientParkingSpotProjection> findAllByClientCpf(String cpf, Pageable pageable);

    Page<ClientParkingSpotProjection> findAllByClientUserId(Long id, Pageable pageable);
}
