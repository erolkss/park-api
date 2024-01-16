package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.ParkingSpot;
import br.com.ero.demoparkapi.service.ParkingSpotService;
import br.com.ero.demoparkapi.web.dto.ParkingSpotCreateDto;
import br.com.ero.demoparkapi.web.dto.ParkingSpotResponseDto;
import br.com.ero.demoparkapi.web.dto.mapper.ParkingSpotMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("api/v1/parkingSpot")
@RestController
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid ParkingSpotCreateDto dto){
        ParkingSpot parkingSpot = ParkingSpotMapper.toParkingSpot(dto);
        parkingSpotService.saveParkingSpot(parkingSpot);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(parkingSpot.getCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpotResponseDto> getByCode(@PathVariable String code){
        ParkingSpot parkingSpot = parkingSpotService.getByCode(code);
        return ResponseEntity.ok(ParkingSpotMapper.toDto(parkingSpot));
    }
}
