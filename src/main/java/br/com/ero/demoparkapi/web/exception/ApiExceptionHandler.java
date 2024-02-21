package br.com.ero.demoparkapi.web.exception;

import br.com.ero.demoparkapi.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({ CodeUniqueViolationException.class})
    public ResponseEntity<ErrorMessage> codeUniqueViolationException(
            CodeUniqueViolationException exception,
            HttpServletRequest httpServletRequest) {
        String message = messageSource.getMessage("exception.codeUniqueViolationException", new Object[]{exception.getResource(), exception.getCode()}, httpServletRequest.getLocale());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(httpServletRequest, HttpStatus.CONFLICT, message));

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(
            AccessDeniedException exception,
            HttpServletRequest httpServletRequest) {

        log.error("Api Error: " + exception);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(httpServletRequest, HttpStatus.FORBIDDEN, exception.getMessage()));

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest httpServletRequest,
            BindingResult bindingResult) {

        log.error("Api Error: " + exception);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(httpServletRequest, HttpStatus.UNPROCESSABLE_ENTITY, messageSource.getMessage("message.invalid.field", null, httpServletRequest.getLocale()), bindingResult, messageSource));

    }
    @ExceptionHandler({UserNameUniqueViolationException.class, CpfUniqueViolationException.class})
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

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(
            RuntimeException exception,
            HttpServletRequest httpServletRequest) {

        log.error("Api Error: " + exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(httpServletRequest, HttpStatus.BAD_REQUEST, exception.getMessage()));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> internServerErrorException(
            Exception exception,
            HttpServletRequest httpServletRequest) {
        ErrorMessage error = new ErrorMessage(
                httpServletRequest,
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );

        log.error("Internal Server Error {} {} ", error, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);

    }



}
