package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.config.entity.Client;
import br.com.ero.demoparkapi.jwt.JwtUserDetails;
import br.com.ero.demoparkapi.service.ClientService;
import br.com.ero.demoparkapi.service.UserService;
import br.com.ero.demoparkapi.web.dto.ClientCreateDto;
import br.com.ero.demoparkapi.web.dto.ClientResponseDto;
import br.com.ero.demoparkapi.web.dto.mapper.ClientMapper;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody @Valid ClientCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.getById(userDetails.getId()));
        clientService.saveClient(client);
        return ResponseEntity.status(201).body(ClientMapper.toDto(client));

    }
}
