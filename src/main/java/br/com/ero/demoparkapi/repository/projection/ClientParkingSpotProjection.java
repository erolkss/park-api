package br.com.ero.demoparkapi.repository.projection;

import br.com.ero.demoparkapi.config.entity.ParkingSpot;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ClientParkingSpotProjection {
    String getPlate();
    String getBrand();
    String getModel();
    String getColor();
    String getClientCpf();
    String getReceipt();
    ParkingSpot getParkingSpot();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getEntryDate();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getExitDate();

    BigDecimal getPrice();
    BigDecimal getDiscount();

}
