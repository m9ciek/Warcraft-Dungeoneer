package com.maciek.warcraftstatstracker.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class CharacterControllerAdvice {

    @ExceptionHandler
    private ResponseEntity<String> handleHttpClientException(HttpClientErrorException e) {
        return new ResponseEntity<>("Character not found or invalid realm name", HttpStatus.NOT_FOUND);
    }
}
