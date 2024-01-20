package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import br.com.ero.demoparkapi.service.ParkingService;
import br.com.ero.demoparkapi.web.dto.ParkingCreateDto;
import br.com.ero.demoparkapi.web.dto.ParkingResponseDto;
import br.com.ero.demoparkapi.web.dto.ParkingSpotResponseDto;
import br.com.ero.demoparkapi.web.dto.mapper.ClientParkingSpotMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/parking")
public class ParkingController {
    private final ParkingService parkingService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkIn(@RequestBody @Valid ParkingCreateDto parkingCreateDto){
        ClientParkingSpot clientParkingSpot = ClientParkingSpotMapper.toClientParkingSpot(parkingCreateDto);
        parkingService.checkIn(clientParkingSpot);
        ParkingResponseDto responseDto = ClientParkingSpotMapper.toDto(clientParkingSpot);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{receipt}")
                .buildAndExpand(clientParkingSpot.getReceipt())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }
}
