package com.example.rent_module.service.impl;

import com.example.rent_module.exception.ApartmentException;
import com.example.rent_module.mapper.RentMapper;
import com.example.rent_module.model.dto.ApartmentRequestDto;
import com.example.rent_module.model.dto.ApartmentResponseDto;
import com.example.rent_module.model.dto.GeoCoderResponseDto;
import com.example.rent_module.model.dto.WeatherResponseDto;
import com.example.rent_module.model.entity.AddressEntity;
import com.example.rent_module.model.entity.ApartmentEntity;
import com.example.rent_module.model.entity.PhotoEntity;
import com.example.rent_module.repository.AddressRepository;
import com.example.rent_module.repository.ApartmentRepository;
import com.example.rent_module.service.IntegrationService;
import com.example.rent_module.service.RentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final AddressRepository addressRepository;
    private final ApartmentRepository apartmentRepository;
    private final RentMapper rentMapper;
    private final IntegrationService integrationService;
    public static final String SUCCESSFUL_REGISTRATION_APARTMENT_MESSAGE = "Apartment is successfully registered";
    public static final String SUCCESSFUL_ADDING_PHOTO_MESSAGE = "Photo of apartment is successfully added";
    public static final String NOT_UNIQUE_APARTMENT_MESSAGE = "Apartment already exists";
    public static final String NOT_FOUND_APARTMENT_MESSAGE = "Apartment does not exists";

    @Override
    public String addApartment(ApartmentRequestDto apartRequest) {
        if (addressRepository.findByAddress(apartRequest.getCityValue(), apartRequest.getStreet(), apartRequest.getNumberOfHouse(), apartRequest.getNumberOfApartment()).isPresent()) {
            throw new ApartmentException(NOT_UNIQUE_APARTMENT_MESSAGE, 700);
        }
        AddressEntity address = rentMapper.apartmentRequestDtoToAddressEntity(apartRequest);
        addressRepository.save(address);
        return SUCCESSFUL_REGISTRATION_APARTMENT_MESSAGE;
    }

    @Override
    public String addPhotoToApartment(Long id, MultipartFile multipartFile) {
        ApartmentEntity apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentException(NOT_FOUND_APARTMENT_MESSAGE));
        try {
            apartment.setPhotoEntity(new PhotoEntity(Base64EncoderDecoder.encode(multipartFile)));
        } catch (IOException e) {
            throw new ApartmentException("Проблема с сериализацией фотографии");
        }
        apartmentRepository.save(apartment);
        return SUCCESSFUL_ADDING_PHOTO_MESSAGE;
    }

    @Override
    public ApartmentResponseDto findApartmentById(Long id) {
        ApartmentEntity apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentException(NOT_FOUND_APARTMENT_MESSAGE, 700));
        try {
            return rentMapper.apartmentEntityToApartmentResponseDto(apartment);
        } catch (IOException e) {
            throw new ApartmentException("Проблема с сериализацией фотографии");
        }
    }

    @Override
    public List<ApartmentResponseDto> findApartmentByLocation(String latitude, String longitude) {
        GeoCoderResponseDto apartmentByLocation = integrationService.findApartmentByLocation(latitude, longitude);
        List<AddressEntity> addressList = addressRepository.findByCity(checkGeoResponse(apartmentByLocation));
        if (addressList.isEmpty()) throw new RuntimeException();
        return addressList.stream()
                .map(x -> {
                    try {
                        return rentMapper.apartmentEntityToApartmentResponseDto(x.getApartment());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    private String checkGeoResponse(GeoCoderResponseDto geoCoderResponseDto) {
        return geoCoderResponseDto.getResultsElemList().get(0).getComponentsObject().getNormalizedCity();
    }

    @Override
    public WeatherResponseDto findWeatherByLocation(String latitude, String longitude) {
        String json = integrationService.findWeatherByLocation(latitude, longitude);
        WeatherResponseDto weatherResponseDto = null;
        try {
            weatherResponseDto = new ObjectMapper().readValue(json.substring(33, json.length() - 3), WeatherResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка преодбразования ответа от сервиса погоды");
        }
        return weatherResponseDto;
    }

    private String parsInfoFromGeo(String locationInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(locationInfo);
            String city = jsonNode.get("results").get(0).get("components").get("_normalized_city").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка обработки запроса от GeoCoder");
        }
        return "";
    }
}
