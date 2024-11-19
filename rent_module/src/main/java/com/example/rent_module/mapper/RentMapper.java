package com.example.rent_module.mapper;

import com.example.rent_module.model.dto.ApartmentRequestDto;
import com.example.rent_module.model.dto.ApartmentResponseDto;
import com.example.rent_module.model.entity.AddressEntity;
import com.example.rent_module.model.entity.ApartmentEntity;
import com.example.rent_module.model.entity.BookingInfoEntity;
import com.example.rent_module.model.entity.UserEntity;
import com.example.rent_module.service.impl.Base64EncoderDecoder;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Base64;

import static java.util.Objects.isNull;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RentMapper {

    @Mapping(target = "city", source = "cityValue")
    AddressEntity apartmentRequestDtoToAddressEntity(ApartmentRequestDto apartRequest);
    @AfterMapping
    default void afterApartmentRequestDtoToAddressEntity(@MappingTarget AddressEntity addressEntity, ApartmentRequestDto apartRequest) {

        addressEntity.setApartment(apartmentRequestDtoToApartmentEntity(apartRequest));
    }

    ApartmentEntity apartmentRequestDtoToApartmentEntity(ApartmentRequestDto apartRequest) ;

    @Mapping(target = "cityValue", source = "address.city")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "numberOfHouse", source = "address.numberOfHouse")
    @Mapping(target = "numberOfApartment", source = "address.numberOfApartment")
    ApartmentResponseDto apartmentEntityToApartmentResponseDto(ApartmentEntity apartmentEntity) throws IOException;
    @AfterMapping
    default void afterApartmentEntityToApartmentResponseDto(@MappingTarget ApartmentResponseDto apartmentResponse, ApartmentEntity apartmentEntity) throws IOException {
        if (!isNull(apartmentEntity.getPhotoEntity())) {
            byte[] photoOfApartmentCoded = apartmentEntity.getPhotoEntity().getPhotoOfApartment();
            byte[] photo = Base64EncoderDecoder.decode(photoOfApartmentCoded);
            apartmentResponse.setPhoto(photo);
        }
    }

    BookingInfoEntity prepareBookingInfoEntityFromParams(LocalDate startDate, LocalDate endDate, ApartmentEntity apartmentEntity, UserEntity userEntity);
}

