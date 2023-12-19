package br.com.ero.demoparkapi.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class ErrorMessage {
    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    private Map<String, String> errors;

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest httpServletRequest, HttpStatus httpStatus, String message) {
        this.path = httpServletRequest.getRequestURI();
        this.method = httpServletRequest.getMethod();
        this.status = httpStatus.value();
        this.statusText = httpStatus.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest httpServletRequest, HttpStatus httpStatus, String message, BindingResult bindingResult   ) {
        this.path = httpServletRequest.getRequestURI();
        this.method = httpServletRequest.getMethod();
        this.status = httpStatus.value();
        this.statusText = httpStatus.getReasonPhrase();
        this.message = message;
        addErrors(bindingResult);
        
    }

    private void addErrors(BindingResult bindingResult) {
        this.errors = new HashMap<>();
        for (FieldError fieldError:bindingResult.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());

        }
    }


}
