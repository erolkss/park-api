package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import br.com.ero.demoparkapi.service.ParkingService;
import br.com.ero.demoparkapi.web.dto.ParkingCreateDto;
import br.com.ero.demoparkapi.web.dto.ParkingResponseDto;
import br.com.ero.demoparkapi.web.dto.ParkingSpotResponseDto;
import br.com.ero.demoparkapi.web.dto.mapper.ClientParkingSpotMapper;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@Tag(name = "Parking", description = "Operations for registering a vehicle's entry and exit from the parking lot.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/parking")
public class ParkingController {
    private final ParkingService parkingService;

    @Operation(
            summary = "Operation checkIn", description = "Resource for entering a vehicle into the parking lot.\n" +
            "Request requires use of a Bearer Token. Access Restricted to Role = 'ADMIN'",security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully", headers = @Header(name = HttpHeaders.LOCATION, description = "Access URI from feature create"), content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ParkingSpotResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Possible causes: <br/>" + "Client CPF not registered in the system; <br/>" + "No free parking spot were found;", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resources not processed due to invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resources not allowed to the CLIENT profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }

    )

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
