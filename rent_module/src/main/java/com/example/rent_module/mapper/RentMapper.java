package com.example.rent_module.mapper;

import com.example.rent_module.model.dto.ApartmentRequestDto;
import com.example.rent_module.model.entity.AddressEntity;
import com.example.rent_module.model.entity.ApartmentEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RentMapper {

    @Mapping(target = "city", source = "cityValue")
    AddressEntity apartmentRequestDtoToAddressEntity(ApartmentRequestDto apartRequest);

    ApartmentRequestDto apartmentEntityToApartmentRequestDto(ApartmentEntity apart);

    @AfterMapping
    default void afterApartmentRequestDtoToAddressEntity(@MappingTarget AddressEntity addressEntity, ApartmentRequestDto apartRequest) {

        addressEntity.setApartment(apartmentRequestDtoToApartmentEntity(apartRequest));
    }

    ApartmentEntity apartmentRequestDtoToApartmentEntity(ApartmentRequestDto apartRequest);
}

