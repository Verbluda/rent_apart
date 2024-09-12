package com.example.rent_module.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "apartment")
public class ApartmentEntity {

    @Id
    @SequenceGenerator(name="apartmentSequence", sequenceName="apartment_sequence", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="apartmentSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "number_of_room")
    private int numberOfRoom;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "price_per_day")
    private double pricePerDay;

    @OneToOne(mappedBy="apartment")
    private AddressEntity address;

    public ApartmentEntity(int numberOfRoom, double pricePerDay) {
        this.numberOfRoom = numberOfRoom;
        this.isAvailable = true;
        this.pricePerDay = pricePerDay;
    }
}
