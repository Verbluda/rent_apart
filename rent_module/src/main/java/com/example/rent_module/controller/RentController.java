package com.example.rent_module.controller;

import com.example.rent_module.model.dto.ApartmentResponseDto;
import com.example.rent_module.service.RentService;
import lombok.RequiredArgsConstructor;
import com.example.rent_module.model.dto.ApartmentRequestDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.rent_module.controller.ControllerConstant.*;

@RestController
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping(REGISTRATION_OF_APARTMENT)
    public String addApartment(@RequestBody ApartmentRequestDto apartRegistration) {
        return rentService.addApartment(apartRegistration);
    }

    @PostMapping(ADD_PHOTO_TO_APARTMENT)
    public String addPhotoToApartment(@RequestParam Long id,
                                      @RequestParam MultipartFile multipartFile) throws IOException {
        return rentService.addPhotoToApartment(id, multipartFile);
    }
    @GetMapping(SHOW_APARTMENT_BY_ID)
    public ApartmentResponseDto findApartmentById(@PathVariable Long id) throws IOException {
        return rentService.findApartmentById(id);
    }

    @GetMapping(FIND_APARTMENT_BY_LOCATION)
    public List<ApartmentResponseDto> findApartmentByLocation(@RequestParam String latitude,
                                                              @RequestParam String longitude) {
        return rentService.findApartmentByLocation(latitude, longitude);
    }
}
