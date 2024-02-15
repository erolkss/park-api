package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import br.com.ero.demoparkapi.repository.projection.ClientParkingSpotProjection;
import br.com.ero.demoparkapi.service.ClientParkingSpotService;
import br.com.ero.demoparkapi.service.ParkingService;
import br.com.ero.demoparkapi.web.dto.PageableDto;
import br.com.ero.demoparkapi.web.dto.ParkingCreateDto;
import br.com.ero.demoparkapi.web.dto.ParkingResponseDto;
import br.com.ero.demoparkapi.web.dto.ParkingSpotResponseDto;
import br.com.ero.demoparkapi.web.dto.mapper.ClientParkingSpotMapper;
import br.com.ero.demoparkapi.web.dto.mapper.PageableMapper;
import br.com.ero.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Parking", description = "Operations for registering a vehicle's entry and exit from the parking lot.")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/parking")
public class ParkingController {
    private final ParkingService parkingService;
    private final ClientParkingSpotService clientParkingSpotService;

    @Operation(
            summary = "Operation checkIn", description = "Resource for entering a vehicle into the parking lot.\n" +
            "Request requires use of a Bearer Token. Access Restricted to Role = 'ADMIN'", security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully", headers = @Header(name = HttpHeaders.LOCATION, description = "Access URI from feature create"), content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ParkingSpotResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Possible causes: <br/>" + "Client CPF not registered in the system; <br/>" + "No free parking spot were found;", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resources not processed due to invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resources not allowed to the CLIENT profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }

    )

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkIn(@RequestBody @Valid ParkingCreateDto parkingCreateDto) {
        ClientParkingSpot clientParkingSpot = ClientParkingSpotMapper.toClientParkingSpot(parkingCreateDto);
        parkingService.checkIn(clientParkingSpot);
        ParkingResponseDto responseDto = ClientParkingSpotMapper.toDto(clientParkingSpot);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{receipt}")
                .buildAndExpand(clientParkingSpot.getReceipt())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/check-in/{receipt}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<ParkingResponseDto> getByReceipt(@PathVariable String receipt) {
        ClientParkingSpot clientParkingSpot = clientParkingSpotService.searchByReceipt(receipt);
        ParkingResponseDto dto = ClientParkingSpotMapper.toDto(clientParkingSpot);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Operation checkOut", description = "Resource for exiting a vehicle into the parking lot.\n" +
            "Request requires use of a Bearer Token. Access Restricted to Role = 'ADMIN'", security = @SecurityRequirement(name = "security"),
            parameters = {@Parameter(in = ParameterIn.PATH, name = "receipt", description = "Receipt number generated by Check-in\n" +
                    "\u200B")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource updated successfully", headers = @Header(name = HttpHeaders.LOCATION, description = "Access URI from feature create"), content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ParkingSpotResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "No receipt number or " + "the vehicle has already been checked out", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resources not allowed to the CLIENT profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }

    )
    @PutMapping("/check-out/{receipt}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkout(@PathVariable String receipt) {
        ClientParkingSpot clientParkingSpot = parkingService.checkOut(receipt);
        ParkingResponseDto dto = ClientParkingSpotMapper.toDto(clientParkingSpot);
        return ResponseEntity.ok(dto);
    }


    @Operation(
            summary = "Find customer parking records by CPF", description = "Find customer parking records by CPF" +
            "Request requires use of a Bearer Token. Access Restricted to Role = 'ADMIN'", security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "cpf", description = "CPF number referring to the client to be consulted", required = true),
                    @Parameter(in = ParameterIn.QUERY, name = "page", description = "Represents the returned page", content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
                    @Parameter(in = ParameterIn.QUERY, name = "size", description = "Represents the total number of elements per page", content = @Content(schema = @Schema(type = "integer", defaultValue = "5"))),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", description = "Standard sort field 'entryDate,asc'",array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "entryDate,asc")), hidden = true)

            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource locate successfully", headers = @Header(name = HttpHeaders.LOCATION, description = "Access URI from feature create"), content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = PageableDto.class))),
                    @ApiResponse(responseCode = "403", description = "Resources not allowed to the CLIENT profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }

    )
    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllParkingSpotByCpf(@PathVariable String cpf, @PageableDefault(size = 5, sort = "entryDate", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<ClientParkingSpotProjection> projection = clientParkingSpotService.getAllByClientCpf(cpf, pageable);
        PageableDto dto = PageableMapper.toDto(projection);
        return ResponseEntity.ok(dto);

    }

}