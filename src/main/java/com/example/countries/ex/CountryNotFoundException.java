package com.example.countries.ex;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String message) {
        super(message);
    }
}
