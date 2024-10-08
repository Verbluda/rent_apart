package com.example.rent_module.service;

import com.example.rent_module.model.dto.ApartmentRequestDto;
import com.example.rent_module.model.dto.ApartmentResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RentService {

    String addApartment(ApartmentRequestDto apart);

    String addPhotoToApartment(Long id, MultipartFile multipartFile) throws IOException;

    ApartmentResponseDto findApartmentById(Long id) throws IOException;

    List<ApartmentResponseDto> findApartmentByLocation(String latitude, String longitude);
}
