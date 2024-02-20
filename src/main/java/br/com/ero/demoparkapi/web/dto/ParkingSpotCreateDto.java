package br.com.ero.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotCreateDto {
    @NotBlank(message = "{NotBlank.ParkingSpotCreateDto.codeParkingSpot}")
    @Size(min = 4, max = 4,  message = "{Size.ParkingSpotCreateDto.codeParkingSpot}")
    private String codeParkingSpot;
    @NotBlank(message = "{NotBlank.ParkingSpotCreateDto.status}")
    @Pattern(regexp = "FREE|BUSY", message = "{Pattern.ParkingSpotCreateDto.status}")
    private String status;
}
