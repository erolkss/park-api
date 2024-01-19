package br.com.ero.demoparkapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingService {
    private final ClientParkingSpotService clientParkingSpotService;
    private final ClientService clientService;
    private final ParkingSpotService parkingSpotService;
}
