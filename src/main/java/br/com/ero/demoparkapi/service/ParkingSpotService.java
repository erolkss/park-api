package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.config.entity.ParkingSpot;
import br.com.ero.demoparkapi.exception.CodeUniqueViolationException;
import br.com.ero.demoparkapi.exception.EntityNotFoundException;
import br.com.ero.demoparkapi.exception.ParkingSpotAvailableException;
import br.com.ero.demoparkapi.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.ero.demoparkapi.config.entity.ParkingSpot.StatusParkingSpot.FREE;

@RequiredArgsConstructor
@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpot saveParkingSpot(ParkingSpot parkingSpot) {
        try {
            return parkingSpotRepository.save(parkingSpot);
        } catch (DataIntegrityViolationException exception) {
            throw new CodeUniqueViolationException("ParkingSpot", parkingSpot.getCodeParkingSpot());

        }
    }

    @Transactional(readOnly = true) 
    public ParkingSpot getByCodeParkingSpot(String codeParkingSpot) {
        return parkingSpotRepository.findByCodeParkingSpot(codeParkingSpot).orElseThrow(
                () -> new EntityNotFoundException("Parking Spot", codeParkingSpot)
        );
    }

    @Transactional(readOnly = true)
    public ParkingSpot searchByParkingSpotFree() {
        return parkingSpotRepository.findFirstByStatus(FREE).orElseThrow(
                () -> new ParkingSpotAvailableException()
        );
    }
}
