package br.com.ero.demoparkapi.service;

import lombok.Getter;

@Getter
public class ReceiptCheckInNotFoundException extends RuntimeException{
    private String receipt;

    public ReceiptCheckInNotFoundException(String receipt) {
        this.receipt = receipt;
    }
}
