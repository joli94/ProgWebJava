package com.proiect.chemdb.controller;

import com.proiect.chemdb.dto.ErrorDto;
import com.proiect.chemdb.exception.BadRequestException;
import com.proiect.chemdb.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(
                ErrorDto.builder().code(404).message(ex.getMessage()).build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleBadRequest(BadRequestException ex) {
        return new ResponseEntity<>(
                ErrorDto.builder().code(400).message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST
        );
    }
}
