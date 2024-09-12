package com.example.rent_module.service.impl;

import com.example.rent_module.mapper.RentMapper;
import com.example.rent_module.model.dto.ApartmentRequestDto;
import com.example.rent_module.model.entity.AddressEntity;
import com.example.rent_module.model.entity.ApartmentEntity;
import com.example.rent_module.repository.AddressRepository;
import com.example.rent_module.repository.ApartmentRepository;
import com.example.rent_module.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    public static final String SUCCESSFUL_REGISTRATION_APARTMENT_MESSAGE = "Apartment is successfully registered";
    public static final String NOT_UNIQUE_APARTMENT_MESSAGE = "Apartment already exists";
    public static final String NOT_FOUND_APARTMENT_MESSAGE = "Apartment does not exists";

    private final AddressRepository addressRepository;
    private final ApartmentRepository apartmentRepository;
    private final RentMapper rentMapper;

    @Override
    public String addApartment(ApartmentRequestDto apartRequest) {
        if (addressRepository.findByAddress(apartRequest.getCityValue(), apartRequest.getStreet(), apartRequest.getNumberOfHouse(), apartRequest.getNumberOfApartment()).isPresent()) {
            throw new RuntimeException(NOT_UNIQUE_APARTMENT_MESSAGE);
        }
        AddressEntity address = rentMapper.apartmentRequestDtoToAddressEntity(apartRequest);
        addressRepository.save(address);
        return SUCCESSFUL_REGISTRATION_APARTMENT_MESSAGE;
    }

    @Override
    public ApartmentRequestDto findApartmentById(Long id) {
        ApartmentEntity apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_APARTMENT_MESSAGE));
        return rentMapper.apartmentEntityToApartmentRequestDto(apartment);
    }
}
