package br.com.ero.demoparkapi.web.dto.mapper;

import br.com.ero.demoparkapi.config.entity.ClientParkingSpot;
import br.com.ero.demoparkapi.web.dto.ParkingCreateDto;
import br.com.ero.demoparkapi.web.dto.ParkingResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class ClientParkingSpotMapper {

    public static ClientParkingSpot toClientParkingSpot(ParkingCreateDto dto) {
        return new ModelMapper().map(dto, ClientParkingSpot.class);
    }

    public static ParkingResponseDto toDto(ClientParkingSpot clientParkingSpot) {
        return new ModelMapper().map(clientParkingSpot, ParkingResponseDto.class);
    }
}
