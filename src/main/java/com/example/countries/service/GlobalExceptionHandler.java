package com.example.countries.service;

import com.example.countries.controller.error.ApiError;
import com.example.countries.ex.CountryNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${api.version}")
    private String apiVersion;

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ApiError> handleCountryNotFoundException(CountryNotFoundException ex, HttpServletRequest request) {
        log.error(request.getRequestURI(), ex);
        return new ResponseEntity<>(
                new ApiError(
                        apiVersion,
                        HttpStatus.NOT_FOUND.toString(),
                        "Photo not found",
                        request.getRequestURI(),
                        ex.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }
}
