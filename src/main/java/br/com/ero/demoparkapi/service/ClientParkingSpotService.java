package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.repository.ClientParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientParkingSpotService {
    private final ClientParkingSpotRepository clientParkingSpotRepository;
}
