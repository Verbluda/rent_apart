package com.example.rent_module.controller;

import com.example.rent_module.model.dto.ApartmentRequestDto;
import com.example.rent_module.model.dto.ApartmentResponseDto;
import com.example.rent_module.model.dto.weather.WeatherResponseDto;
import com.example.rent_module.service.CheckValidToken;
import com.example.rent_module.service.RentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.rent_module.controller.ControllerConstant.*;

@RestController
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;
    private final CheckValidToken checkValidToken;

    @PostMapping(REGISTRATION_OF_APARTMENT)
    public String addApartment(@RequestBody ApartmentRequestDto apartRegistration,
                               @RequestHeader String token) {
        checkValidToken.checkToken(token);
        return rentService.addApartment(apartRegistration);
    }

    @PostMapping(ADD_PHOTO_TO_APARTMENT)
    public String addPhotoToApartment(@RequestParam Long id,
                                      @RequestParam MultipartFile multipartFile) {
        return rentService.addPhotoToApartment(id, multipartFile);
    }
    @GetMapping(SHOW_APARTMENT_BY_ID)
    public ApartmentResponseDto findApartmentById(@PathVariable Long id) {
        return rentService.findApartmentById(id);
    }

    @GetMapping(FIND_APARTMENT_BY_LOCATION)
    public List<ApartmentResponseDto> findApartmentByLocation(@RequestParam String latitude,
                                                              @RequestParam String longitude) {
        return rentService.findApartmentByLocation(latitude, longitude);
    }

    @GetMapping(FIND_WEATHER_BY_LOCATION)
    public WeatherResponseDto findWeatherByLocation(@RequestParam String latitude,
                                                    @RequestParam String longitude) {
        return rentService.findWeatherByLocation(latitude, longitude);
    }
}
