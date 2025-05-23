package com.example.countries.service;

import com.example.countries.model.CountryJson;
import com.example.grpc.countries.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CountryGRPCService extends CountryServiceGrpc.CountryServiceImplBase {

    CountryService countryService;

    @Autowired
    public CountryGRPCService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public StreamObserver<CountryRequest> create(StreamObserver<CreateSummaryResponse> responseObserver) {
        return new StreamObserver<CountryRequest>() {
            List<String> countries = new ArrayList<>();
            List<String> errors = new ArrayList<>();

            @Override
            public void onNext(CountryRequest value) {
                try {
                    CountryJson countryJson = new CountryJson(
                            null,
                            value.getTitle(),
                            value.getCode()
                    );
                    countryJson = countryService.createCountry(countryJson);
                    countries.add(countryJson.id().toString());
                } catch (Exception e) {
                    errors.add(e.getMessage());
                }
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                CreateSummaryResponse createSummaryResponse = CreateSummaryResponse.newBuilder()
                        .setCreatedCount(countries.size())
                        .addAllErrors(errors)
                        .build();
                responseObserver.onNext(createSummaryResponse);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void getCountry(IdCountry request, StreamObserver<CountryResponse> responseObserver) {
        CountryJson countryJson = countryService.findCountryById(
                request.getId()
        );
        responseObserver.onNext(CountryResponse.newBuilder()
                .setId(countryJson.id().toString())
                .setTitle(countryJson.title())
                .setCode(countryJson.code())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAll(Empty request, StreamObserver<GetAllCountryResponse> responseObserver) {
        List<CountryResponse> countriesJson = countryService.findAllCountries()
                .stream().map(country -> CountryResponse.newBuilder()
                        .setId(country.id().toString())
                        .setTitle(country.title())
                        .setCode(country.code())
                        .build())
                .toList();
        GetAllCountryResponse response = GetAllCountryResponse.newBuilder()
                .addAllCountries(countriesJson)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(CountryResponse request, StreamObserver<CountryResponse> responseObserver) {
        countryService.editCountry(new CountryJson(
                UUID.fromString(request.getId()),
                request.getTitle(),
                request.getCode()));
        CountryResponse response = CountryResponse.newBuilder()
                .setId(request.getId())
                .setCode(request.getCode())
                .setTitle(request.getTitle())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
