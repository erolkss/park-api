package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import br.com.ero.demoparkapi.exception.EntityNotFoundException;
import br.com.ero.demoparkapi.repository.ClientParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientParkingSpotService {
    private final ClientParkingSpotRepository clientParkingSpotRepository;

    @Transactional
    public ClientParkingSpot saveClientParkingSpot(ClientParkingSpot clientParkingSpot) {
        return clientParkingSpotRepository.save(clientParkingSpot);
    }

    @Transactional(readOnly = true)
    public ClientParkingSpot searchByReceipt(String receipt) {
        return clientParkingSpotRepository.findByReceiptAndExitDateIsNull(receipt).orElseThrow(
                () -> new EntityNotFoundException(String.format("Receipt '%s' Not Found in the System or check-out already done.", receipt))
        );
    }
}
