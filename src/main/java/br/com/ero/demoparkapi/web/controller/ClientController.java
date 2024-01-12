package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.Client;
import br.com.ero.demoparkapi.jwt.JwtUserDetails;
import br.com.ero.demoparkapi.repository.projection.ClientProjection;
import br.com.ero.demoparkapi.service.ClientService;
import br.com.ero.demoparkapi.service.UserService;
import br.com.ero.demoparkapi.web.dto.ClientCreateDto;
import br.com.ero.demoparkapi.web.dto.ClientResponseDto;
import br.com.ero.demoparkapi.web.dto.PageableDto;
import br.com.ero.demoparkapi.web.dto.UserResponseDto;
import br.com.ero.demoparkapi.web.dto.mapper.ClientMapper;
import br.com.ero.demoparkapi.web.dto.mapper.PageableMapper;
import br.com.ero.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

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
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "CPF client already registered in the system", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resources not processed due to invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resources not allowed to the ADMIN profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody @Valid ClientCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.getById(userDetails.getId()));
        clientService.saveClient(client);
        return ResponseEntity.status(201).body(ClientMapper.toDto(client));

    }

    @Operation(
            summary = "Search Client", description = "Resource to search Client for By ID \n" +
            "\"Request requires use of a Bearer Token. Access Restricted to Role = 'ADMIN'\"",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resources not allowed to the CLIENT profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> getByIdClient(@PathVariable Long id) {
        Client client = clientService.getId(id);
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }


    @Operation(
            summary = "Retrieve list of clients",
            description = "Request requires use of a Bearer Token. Access Restricted to Role = 'ADMIN'",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page", content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),description = "Represents the returned page"),
                    @Parameter(in = QUERY, name = "size", content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),description = "Represents the total number of elements per page"),
                    @Parameter(in = QUERY, hidden = true, name = "sort", content = @Content(schema = @Schema(type = "string", defaultValue = "id,asc")),description = "Represents the ordering of results. Multiple sorting criteria are supported.")

            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieve successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Resources not allowed to the CLIENT profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllClient(@Parameter(hidden = true) @PageableDefault(size = 4, sort = {"name"}) Pageable pageable) {
        Page<ClientProjection> client = clientService.getAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(client));
    }
}
