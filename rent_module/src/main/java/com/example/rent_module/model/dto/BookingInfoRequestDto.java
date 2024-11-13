package com.example.rent_module.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingInfoRequestDto {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int apartmentId;
}
