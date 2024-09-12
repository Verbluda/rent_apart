package com.example.rent_module.service;

import com.example.rent_module.model.dto.ApartmentRequestDto;

public interface RentService {

    String addApartment(ApartmentRequestDto apart);

    ApartmentRequestDto findApartmentById(Long id);
}
