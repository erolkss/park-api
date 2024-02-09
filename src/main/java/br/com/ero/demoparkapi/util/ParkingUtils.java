package br.com.ero.demoparkapi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {

    public static String generateReceipt() {
        LocalDateTime date = LocalDateTime.now();
        String receipt = date.toString().substring(0, 19);
        return receipt.replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }

    private static final double PRIMEIROS_15_MINUTES = 5.00;
    private static final double PRIMEIROS_60_MINUTES = 9.25;
    private static final double ADICIONAL_15_MINUTES = 1.75;


    public static BigDecimal calculateCost(LocalDateTime entry, LocalDateTime exit) {
        long minutes = entry.until(exit, ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {

            // complete com a lógica para calcular o custo até 15 minutos de uso
            total = PRIMEIROS_15_MINUTES;

        } else if (minutes <= 60) {
            total = PRIMEIROS_60_MINUTES;

            // complete com a lógica para calcular o custo até os primeiros 60 minutos de uso

        } else {
            long addicionalMinutes = minutes - 60;
            Double totalParts = ((double) addicionalMinutes / 15);
            if (totalParts > totalParts.intValue()) {
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * (totalParts.intValue() + 1));
            } else {
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * totalParts.intValue());
            }
        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }
}
