package com.example.rent_module.service;

public interface IntegrationService {

    String integrate1();
    String integrate2();
    String integrate3();
    String findApartmentByLocation(String latitude, String longitude);
    String findWeatherByLocation(String latitude, String longitude);
}
