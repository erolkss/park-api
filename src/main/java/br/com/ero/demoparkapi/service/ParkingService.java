package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.config.entity.Client;
import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import br.com.ero.demoparkapi.config.entity.ParkingSpot;
import br.com.ero.demoparkapi.util.ParkingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Transactional
    public ClientParkingSpot checkOut(String receipt) {
        ClientParkingSpot clientParkingSpot = clientParkingSpotService.searchByReceipt(receipt);

        LocalDateTime exitDate = LocalDateTime.now();

        BigDecimal value = ParkingUtils.calculateCost(clientParkingSpot.getEntryDate(), exitDate);
        clientParkingSpot.setPrice(value);

        long totalNumberOfTimes = clientParkingSpotService.getTotalParkingTimes(clientParkingSpot.getClient().getCpf());

        BigDecimal discount = ParkingUtils.calculateDiscount(value, totalNumberOfTimes);
        clientParkingSpot.setDiscount(discount);

        clientParkingSpot.setExitDate(exitDate);
        clientParkingSpot.getParkingSpot().setStatus(ParkingSpot.StatusParkingSpot.FREE);

        return clientParkingSpotService.saveClientParkingSpot(clientParkingSpot);
    }
}
