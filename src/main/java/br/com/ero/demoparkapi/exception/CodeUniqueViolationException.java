package br.com.ero.demoparkapi.exception;

public class CodeUniqueViolationException extends RuntimeException {
    public CodeUniqueViolationException(String message){
        super(message);
    }
}
