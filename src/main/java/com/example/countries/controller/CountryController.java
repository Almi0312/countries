package com.example.countries.controller;

import com.example.countries.model.CountryJson;
import com.example.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/country")
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

    @GetMapping("/{id}")
    public CountryJson findById(@PathVariable("id") String id) {
        return countryService.findCountryById(id);
    }
}
