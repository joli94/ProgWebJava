package com.home.dogs.rest;

import com.home.dogs.dto.ErrorResponse;
import com.home.dogs.exceptions.BadRequestException;
import com.home.dogs.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException exception) {
        log.debug("Entiy not found " + exception);

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(404)
                .message(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleEntityNotFound(BadRequestException exception) {
        log.debug("Bad request  " + exception);

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(400)
                .message(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> hadleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.debug("Invalid request  " + exception);

        String invalidFields = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField())
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(400)
                .message("The following fields are invalid: " + invalidFields)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
