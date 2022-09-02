package br.com.pines.dev.exception.handler;

import br.com.pines.dev.dto.ApiErrorDto;
import br.com.pines.dev.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<Object> handleIdNotFoundException(IdNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiErrorDto.builder()
                .message("Not found with given id")
                .timestamp(Instant.now())
                .build());

    }
}
