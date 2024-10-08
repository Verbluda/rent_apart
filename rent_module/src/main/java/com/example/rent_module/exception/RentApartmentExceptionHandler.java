package com.example.rent_module.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RentApartmentExceptionHandler {

    @ExceptionHandler(ApartmentException.class)
    public void catchException(ApartmentException e) {

        preparedResponseMessage(e.getExceptionCode(), e.getMessage());
    }

    @ExceptionHandler(AddressException.class)
    public void catchException(AddressException e) {

        preparedResponseMessage(e.getExceptionCode(), e.getMessage());
    }

    private ResponseEntity<?> preparedResponseMessage(int code, String message) {

        return ResponseEntity.status(code).body(message);
    }
}
