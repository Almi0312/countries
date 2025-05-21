package com.example.countries.controller;

import com.example.countries.model.CountryJson;
import com.example.countries.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/country")
@Slf4j
public class CountryController {

    CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CountryJson create(@RequestBody CountryJson countryJson) {
        return countryService.createCountry(countryJson);
    }

    @PatchMapping("/edit")
    public CountryJson edit(@RequestBody CountryJson countryJson) {
        return countryService.editCountry(countryJson);
    }

    @GetMapping("/all")
    public List<CountryJson> findAll() {
        return countryService.findAllCountries();
    }

    @GetMapping("/")
    public CountryJson findById(@RequestParam(name = "id") String id) {
        return countryService.findCountryById(id);
    }

    //Главный минус, что в таком виде работает в рамках контроллера и не глобален
//    @ExceptionHandler(CountryNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public void handleCountryNotFoundException(CountryNotFoundException ex, HttpServletRequest request) {
//        log.error("Error while founding country", ex);
//        log.error(request.getRequestURI(), ex);
//
//    }
}
