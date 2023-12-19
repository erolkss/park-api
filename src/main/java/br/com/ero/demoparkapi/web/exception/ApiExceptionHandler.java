package br.com.ero.demoparkapi.web.exception;

import br.com.ero.demoparkapi.exception.EntityNotFoundException;
import br.com.ero.demoparkapi.exception.UserNameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest httpServletRequest,
            BindingResult bindingResult) {

        log.error("Api Error: " + exception);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(httpServletRequest, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Fields", bindingResult));

    }
    @ExceptionHandler(UserNameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> uniqueViolationException(
            RuntimeException exception,
            HttpServletRequest httpServletRequest) {

        log.error("Api Error: " + exception);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(httpServletRequest, HttpStatus.CONFLICT, exception.getMessage()));

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(
            RuntimeException exception,
            HttpServletRequest httpServletRequest) {

        log.error("Api Error: " + exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(httpServletRequest, HttpStatus.NOT_FOUND, exception.getMessage()));

    }

}
