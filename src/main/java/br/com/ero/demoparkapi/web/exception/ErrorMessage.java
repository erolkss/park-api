package br.com.ero.demoparkapi.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Getter
@ToString
public class ErrorMessage {
    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    public ErrorMessage(HttpServletRequest httpServletRequest, HttpStatus httpStatus, String message, BindingResult bindingResult, MessageSource messageSource) {
        this.path = httpServletRequest.getRequestURI();
        this.method = httpServletRequest.getMethod();
        this.status = httpStatus.value();
        this.statusText = httpStatus.getReasonPhrase();
        this.message = message;
        addErrors(bindingResult, messageSource, httpServletRequest.getLocale());
    }

    private void addErrors(BindingResult bindingResult, MessageSource messageSource, Locale locale) {
        this.errors = new HashMap<>();
        for (FieldError fieldError:bindingResult.getFieldErrors()) {
            String code = Objects.requireNonNull(fieldError.getCodes())[0];
            String message = messageSource.getMessage(code, fieldError.getArguments(), locale);
            this.errors.put(fieldError.getField(), message);

        }
    }

    private void addErrors(BindingResult bindingResult) {
        this.errors = new HashMap<>();
        for (FieldError fieldError:bindingResult.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());

        }
    }


}
