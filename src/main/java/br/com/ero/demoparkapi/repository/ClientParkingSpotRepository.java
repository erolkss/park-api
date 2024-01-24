package br.com.ero.demoparkapi.repository;

import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientParkingSpotRepository extends JpaRepository<ClientParkingSpot, Long> {
}