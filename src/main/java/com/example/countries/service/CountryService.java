package com.example.countries.service;

import com.example.countries.data.entity.CountryEntity;
import com.example.countries.data.repository.CountryRepository;
import com.example.countries.model.CountryJson;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CountryService {

    CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @NonNull
    public CountryJson createCountry(@NonNull CountryJson countryJson) {
        return CountryJson.convertInJson(countryRepository.save(CountryEntity.convertInEntity(countryJson)));
    }

    @NonNull
    public CountryJson editCountry(@NonNull CountryJson countryJson) {
        return countryRepository.findById(countryJson.id()).map(countryEntity -> {
            countryEntity.setTitle(countryJson.title());
            countryEntity.setCode(countryJson.code());
            return CountryJson.convertInJson(countryEntity);
        }).orElseThrow(
                () -> new RuntimeException("Страны с id %s не существует".formatted(countryJson.id()))
        );
    }

    public List<CountryJson> findAllCountries() {
        return countryRepository.findAll().stream().map(CountryJson::convertInJson).toList();
    }

    public CountryJson findCountryByTitle(String title) {
        return countryRepository.findByTitle(title).map(CountryJson::convertInJson)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Страны с названием %s не существует".formatted(title)));
    }

    public CountryJson findCountryById(String id) {
        return countryRepository.findById(UUID.fromString(id)).map(CountryJson::convertInJson)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Страны с id %s не существует".formatted(id)));
    }
}
