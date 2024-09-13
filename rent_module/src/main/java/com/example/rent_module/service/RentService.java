package com.example.rent_module.service;

import com.example.rent_module.model.dto.ApartmentRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RentService {

    String addApartment(ApartmentRequestDto apart) throws IOException;
    ApartmentRequestDto findApartmentById(Long id);
    String addPhotoToApartment(Long id, MultipartFile multipartFile) throws IOException;
}
