package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.config.entity.Client;
import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import br.com.ero.demoparkapi.config.entity.ParkingSpot;
import br.com.ero.demoparkapi.util.ParkingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ClientParkingSpotService clientParkingSpotService;
    private final ClientService clientService;
    private final ParkingSpotService parkingSpotService;

    @Transactional
    public ClientParkingSpot checkIn(ClientParkingSpot clientParkingSpot) {
        Client client = clientService.searchByCpf(clientParkingSpot.getClient().getCpf());
        clientParkingSpot.setClient(client);

        ParkingSpot parkingSpot = parkingSpotService.searchByParkingSpotFree();
        parkingSpot.setStatus(ParkingSpot.StatusParkingSpot.BUSY);
        clientParkingSpot.setParkingSpot(parkingSpot);

        clientParkingSpot.setEntryDate(LocalDateTime.now());

        clientParkingSpot.setReceipt(ParkingUtils.generateReceipt());

        return clientParkingSpotService.saveClientParkingSpot(clientParkingSpot);
    }
}
