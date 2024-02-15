package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import br.com.ero.demoparkapi.exception.EntityNotFoundException;
import br.com.ero.demoparkapi.jwt.JwtUserDetails;
import br.com.ero.demoparkapi.repository.ClientParkingSpotRepository;
import br.com.ero.demoparkapi.repository.projection.ClientParkingSpotProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public long getTotalParkingTimes(String cpf) {
        return  clientParkingSpotRepository.countByClientCpfAndExitDateIsNotNull(cpf);
    }
    @Transactional(readOnly = true)
    public Page<ClientParkingSpotProjection> getAllByClientCpf(String cpf, Pageable pageable) {
        return clientParkingSpotRepository.findAllByClientCpf(cpf, pageable);
    }


    @Transactional(readOnly = true)
    public Page<ClientParkingSpotProjection> getAllByUserId(JwtUserDetails id, Pageable pageable) {
        return clientParkingSpotRepository.findAllByClientUserId(id, pageable);
    }
}
