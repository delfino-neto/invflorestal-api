package br.com.inv.florestal.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static br.com.inv.florestal.api.handler.AppErrorCodes.BAD_CREDENTIALS;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
            .appErrorCode(BAD_CREDENTIALS.getCode())
            .appErrorDescription(BAD_CREDENTIALS.getDescription())
            .error(BAD_CREDENTIALS.getDescription())
            .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp){
        Set<String> errors = new HashSet<>();

        exp.getBindingResult().getAllErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
            .validationErrors(errors)
            .build()
        );
    }
}
