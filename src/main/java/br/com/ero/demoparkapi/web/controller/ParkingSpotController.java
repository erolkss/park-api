package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.ParkingSpot;
import br.com.ero.demoparkapi.service.ParkingSpotService;
import br.com.ero.demoparkapi.web.dto.ParkingSpotCreateDto;
import br.com.ero.demoparkapi.web.dto.ParkingSpotResponseDto;
import br.com.ero.demoparkapi.web.dto.UserResponseDto;
import br.com.ero.demoparkapi.web.dto.mapper.ParkingSpotMapper;
import br.com.ero.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Parking Spot", description = "Contains all operations related to the parking spot resource")
@RequestMapping("api/v1/parkingSpot")
@RestController
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @Operation(
            summary = "Create a new Parking Spot", description = "Resource to create a new Parking Spot.\n" +
            "Request requires use of a Bearer Token. Access Restricted to Role = 'ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully", headers = @Header(name = HttpHeaders.LOCATION, description = "URI from feature create")),
                    @ApiResponse(responseCode = "409", description = "Parking Spot already registered in the System", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resources not processed due to invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }

    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid ParkingSpotCreateDto dto) {
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
    public ResponseEntity<ParkingSpotResponseDto> getByCode(@PathVariable String code) {
        ParkingSpot parkingSpot = parkingSpotService.getByCode(code);
        return ResponseEntity.ok(ParkingSpotMapper.toDto(parkingSpot));
    }
}
