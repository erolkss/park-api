package br.com.ero.demoparkapi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingResponseDto {
    private String plate;
    private String brand;
    private String model;
    private String color;
    private String clientCpf;
    private String receipt;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime entryDate;
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime exitDate;
    private String codeParkingSpot;
    private BigDecimal value;
    private BigDecimal discount;

}
