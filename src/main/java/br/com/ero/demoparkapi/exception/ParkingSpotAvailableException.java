package br.com.ero.demoparkapi.exception;

public class ParkingSpotAvailableException extends RuntimeException {
    public ParkingSpotAvailableException(String message) {
        super(message);
    }

}
