package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.config.entity.ParkingSpot;
import br.com.ero.demoparkapi.exception.CodeUniqueViolationException;
import br.com.ero.demoparkapi.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpot saveParkingSpot(ParkingSpot parkingSpot) throws DataIntegrityViolationException{
        try {
            return parkingSpotRepository.save(parkingSpot);
        } catch (DataIntegrityViolationException exception) {
            throw new CodeUniqueViolationException(String.format("ParkingSpot with code '%s' already registered", parkingSpot.getCode()));

        }
    }
}
