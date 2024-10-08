package com.example.rent_module.service.impl;

import com.example.rent_module.exception.ApartmentException;
import com.example.rent_module.mapper.RentMapper;
import com.example.rent_module.model.dto.ApartmentRequestDto;
import com.example.rent_module.model.dto.ApartmentResponseDto;
import com.example.rent_module.model.entity.AddressEntity;
import com.example.rent_module.model.entity.ApartmentEntity;
import com.example.rent_module.model.entity.PhotoEntity;
import com.example.rent_module.repository.AddressRepository;
import com.example.rent_module.repository.ApartmentRepository;
import com.example.rent_module.service.IntegrationService;
import com.example.rent_module.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    public String addPhotoToApartment(Long id, MultipartFile multipartFile) throws IOException {
        ApartmentEntity apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_APARTMENT_MESSAGE));
        apartment.setPhotoEntity(new PhotoEntity(Base64EncoderDecoder.encode(multipartFile)));
        apartmentRepository.save(apartment);
        return SUCCESSFUL_ADDING_PHOTO_MESSAGE;
    }

    @Override
    public ApartmentResponseDto findApartmentById(Long id) throws IOException {
        ApartmentEntity apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentException(NOT_FOUND_APARTMENT_MESSAGE, 700));
        return rentMapper.apartmentEntityToApartmentResponseDto(apartment);
    }

    @Override
    public List<ApartmentResponseDto> findApartmentByLocation(String latitude, String longitude) {
        integrationService.findByLocation(latitude, longitude);
        return null;
    }
}
