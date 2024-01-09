package br.com.ero.demoparkapi.web.dto.mapper;


import br.com.ero.demoparkapi.config.entity.Client;
import br.com.ero.demoparkapi.web.dto.ClientCreateDto;
import br.com.ero.demoparkapi.web.dto.ClientResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDto dto) {
        return new ModelMapper().map(dto, Client.class);
    }

    public static ClientResponseDto toDto(Client client) {
        return new ModelMapper().map(client, ClientResponseDto.class);
    }
}
