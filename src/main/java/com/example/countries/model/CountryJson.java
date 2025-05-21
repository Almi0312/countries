package com.example.countries.model;

import com.example.countries.data.entity.CountryEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.util.UUID;

public record CountryJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("title")
        String title,
        @JsonProperty("code")
        String code
) {

    public static @NonNull CountryJson convertInJson(CountryEntity entity) {
        return new CountryJson(
                entity.getId(),
                entity.getTitle(),
                entity.getCode());
    }
}