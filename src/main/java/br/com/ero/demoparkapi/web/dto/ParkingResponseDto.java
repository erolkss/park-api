package br.com.ero.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingResponseDto {
    private String plate;
    private String brand;
    private String model;
    private String color;
    private String clientCpf;
    private String receipt;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private String codeParkingSpot;
    private BigDecimal value;
    private BigDecimal discount;

}
