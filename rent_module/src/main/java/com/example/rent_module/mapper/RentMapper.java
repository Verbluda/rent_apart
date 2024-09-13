package com.example.rent_module.mapper;

import com.example.rent_module.model.dto.ApartmentRequestDto;
import com.example.rent_module.model.entity.AddressEntity;
import com.example.rent_module.model.entity.ApartmentEntity;
import com.example.rent_module.model.entity.PhotoEntity;
import com.example.rent_module.service.impl.Base64EncoderDecoder;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RentMapper {

    @Mapping(target = "city", source = "cityValue")
    AddressEntity apartmentRequestDtoToAddressEntity(ApartmentRequestDto apartRequest) throws IOException;
    @AfterMapping
    default void afterApartmentRequestDtoToAddressEntity(@MappingTarget AddressEntity addressEntity, ApartmentRequestDto apartRequest) throws IOException {

        addressEntity.setApartment(apartmentRequestDtoToApartmentEntity(apartRequest));
    }

    ApartmentRequestDto apartmentEntityToApartmentRequestDto(ApartmentEntity apart);

    ApartmentEntity apartmentRequestDtoToApartmentEntity(ApartmentRequestDto apartRequest) throws IOException;
}

