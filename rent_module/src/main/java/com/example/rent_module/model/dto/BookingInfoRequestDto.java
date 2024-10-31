package com.example.rent_module.model.dto;

import com.example.rent_module.model.entity.ApartmentEntity;
import com.example.rent_module.model.entity.UserEntity;

import java.time.LocalDateTime;

@Data
public class BookingInfoRequestDto {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int apartmentId;
}
