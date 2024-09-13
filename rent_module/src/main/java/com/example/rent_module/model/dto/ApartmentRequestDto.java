package com.example.rent_module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ApartmentRequestDto {

    private int numberOfRoom;
    private boolean isAvailable;
    private double pricePerDay;
    private String cityValue;
    private String street;
    private String numberOfHouse;
    private String numberOfApartment;
    private MultipartFile photo;
}
