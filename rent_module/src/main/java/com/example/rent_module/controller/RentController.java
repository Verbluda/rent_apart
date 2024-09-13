package com.example.rent_module.controller;

import com.example.rent_module.service.RentService;
import lombok.RequiredArgsConstructor;
import com.example.rent_module.model.dto.ApartmentRequestDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.rent_module.controller.ControllerConstant.*;

@RestController
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping(REGISTRATION_OF_APARTMENT)
    public String addApartment(@RequestBody ApartmentRequestDto apartRegistration) throws IOException {
        return rentService.addApartment(apartRegistration);
    }

    @PostMapping(ADD_PHOTO_TO_APARTMENT)
    public String addPhotoToApartment(@RequestParam Long id,
                                      @RequestParam MultipartFile multipartFile) throws IOException {
        return rentService.addPhotoToApartment(id, multipartFile);
    }
    @GetMapping(SHOW_APARTMENT_BY_ID)
    public ApartmentRequestDto findApartmentById(@PathVariable Long id) {
        return rentService.findApartmentById(id);
    }
}
