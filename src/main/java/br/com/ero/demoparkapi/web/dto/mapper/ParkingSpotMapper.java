package br.com.ero.demoparkapi.web.dto.mapper;

import br.com.ero.demoparkapi.config.entity.ParkingSpot;
import br.com.ero.demoparkapi.web.dto.ParkingSpotCreateDto;
import br.com.ero.demoparkapi.web.dto.ParkingSpotResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpotMapper {

    public static ParkingSpot toParkingSpot(ParkingSpotCreateDto dto) {
        return new ModelMapper().map(dto, ParkingSpot.class);
    }

    public static ParkingSpotResponseDto toDto(ParkingSpot parkingSpot) {
        return new ModelMapper().map(parkingSpot, ParkingSpotResponseDto.class);
    }


}
