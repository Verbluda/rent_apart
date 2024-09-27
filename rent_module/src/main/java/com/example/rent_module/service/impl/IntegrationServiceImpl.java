package com.example.rent_module.service.impl;

import com.example.rent_module.model.TestObjectDto;
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

    RestTemplate restTemplate = new RestTemplate();
    public static final String URL_1 = "http://localhost:9696/api/product/test1";
    public static final String URL_2 = "http://localhost:9696/api/product/test2?text=%s";
    public static final String URL_3 = "http://localhost:9696/api/product/test3";

    @Override
    public String integrate1() {
        String body = restTemplate.exchange(URL_1,
                HttpMethod.GET,
                new HttpEntity<>(null, getHeaders()),
                String.class).getBody();
        return body;
    }

    @Override
    public String integrate2() {
        String body = restTemplate.exchange(prepareUrl(),
                HttpMethod.GET,
                new HttpEntity<>(null, getHeaders()),
                String.class).getBody();
        return body;
    }

    @Override
    public String integrate3() {
        String body = restTemplate.exchange(URL_3,
                HttpMethod.POST,
                new HttpEntity<>(new TestObjectDto(2, "имя"), getHeaders()),
                String.class).getBody();
        return body;
    }

    private String prepareUrl() {
        String text = "table";
        return String.format(URL_2, text);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "token");
        return headers;
    }

}
