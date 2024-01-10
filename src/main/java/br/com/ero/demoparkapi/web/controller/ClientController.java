package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.Client;
import br.com.ero.demoparkapi.jwt.JwtUserDetails;
import br.com.ero.demoparkapi.service.ClientService;
import br.com.ero.demoparkapi.service.UserService;
import br.com.ero.demoparkapi.web.dto.ClientCreateDto;
import br.com.ero.demoparkapi.web.dto.ClientResponseDto;
import br.com.ero.demoparkapi.web.dto.UserResponseDto;
import br.com.ero.demoparkapi.web.dto.mapper.ClientMapper;
import br.com.ero.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Clients", description = "Contains all operations related to a client's resource")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @Operation(
            summary = "Create a new Client", description = "Resource to create a new client linked to a Registered user.\n" +
            "Request requires use of a Bearer Token. Access Restricted to Role = 'CLIENT'",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "CPF client already registered in the system", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resources not processed due to invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resources not allowed to the ADMIN profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody @Valid ClientCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.getById(userDetails.getId()));
        clientService.saveClient(client);
        return ResponseEntity.status(201).body(ClientMapper.toDto(client));

    }
}