package com.example.rent_module.service.impl;

import com.example.rent_module.model.TestObjectDto;
import com.example.rent_module.model.dto.geo_coder.GeoCoderResponseDto;
import com.example.rent_module.model.dto.weather.WeatherResponseDto;
import com.example.rent_module.model.entity.IntegrationEntity;
import com.example.rent_module.repository.IntegrationRepository;
import com.example.rent_module.service.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

    private final IntegrationRepository integrationRepository;
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    public static final String URL_1 = "http://localhost:9696/api/product/test1";
    public static final String URL_2 = "http://localhost:9696/api/product/test2?text=%s";
    public static final String URL_3 = "http://localhost:9696/api/product/test3";
    public static final String GEO = "GEO";
    public static final String WEATHER = "WEATHER";
    public static final String NOT_FOUND_INTEGRATION_INFO = "Information about this integration is not found";

    @Override
    public String integrate1() {
        headers.add("token", "token");
        return restTemplate.exchange(URL_1,
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                String.class).getBody();
    }

    @Override
    public String integrate2() {
        headers.add("token", "token");
        return restTemplate.exchange(prepareUrl2(),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                String.class).getBody();
    }

    @Override
    public String integrate3() {
        headers.add("token", "token");
        return restTemplate.exchange(URL_3,
                HttpMethod.POST,
                new HttpEntity<>(new TestObjectDto(2, "имя"), headers),
                String.class).getBody();
    }

    @Override
    public GeoCoderResponseDto findApartmentByLocation(String latitude, String longitude) {
        return restTemplate.exchange(prepareUrlForGeo(latitude, longitude),
                HttpMethod.GET,
                new HttpEntity<>(null, null),
                GeoCoderResponseDto.class).getBody();
    }

    @Override
    public WeatherResponseDto findWeatherByLocation(String latitude, String longitude) {
        return restTemplate.exchange(preparedUrlForWeather(latitude, longitude),
                HttpMethod.GET,
                new HttpEntity<>(null, null),
                WeatherResponseDto.class).getBody();
    }

    private String prepareUrl2() {
        String text = "table";
        return String.format(URL_2, text);
    }

    private String prepareUrlForGeo(String latitude, String longitude) {
        IntegrationEntity integrationEntity = integrationRepository.findById(GEO)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_INTEGRATION_INFO));
        return String.format(integrationEntity.getPathValue(),
                latitude, longitude, Base64EncoderDecoder.decode(integrationEntity.getTokenValue()));
    }

    private String preparedUrlForWeather(String latitude, String longitude) {
        IntegrationEntity integrationEntity = integrationRepository.findById(WEATHER)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_INTEGRATION_INFO));
        return String.format(integrationEntity.getPathValue(),
                Base64EncoderDecoder.decode(integrationEntity.getTokenValue()), latitude, longitude);
    }

    private HttpHeaders setHeaders() {
        return null;
    }
}
