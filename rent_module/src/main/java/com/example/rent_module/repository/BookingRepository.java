package com.example.rent_module.repository;

import com.example.rent_module.model.entity.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingInfoEntity, Long> {

//    @Query(value = "select b from BookingInfoEntity b where b.startDate < :startDate or b.endDate > :endDAte or (b.startDate > :startDate and b.endDate < :endDAte)")
//    List<BookingInfoEntity> findOverlapBookingInfo(LocalDate startDate, LocalDate endDate);
}
