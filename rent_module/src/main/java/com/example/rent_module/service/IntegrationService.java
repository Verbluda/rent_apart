package com.example.rent_module.service;

import com.example.rent_module.model.dto.geo_coder.GeoCoderResponseDto;
import com.example.rent_module.model.dto.weather.WeatherResponseDto;

public interface IntegrationService {

    String integrate1();
    String integrate2();
    String integrate3();
//    String findApartmentByLocation(String latitude, String longitude);
    GeoCoderResponseDto findApartmentByLocation(String latitude, String longitude);
    WeatherResponseDto findWeatherByLocation(String latitude, String longitude);
}
