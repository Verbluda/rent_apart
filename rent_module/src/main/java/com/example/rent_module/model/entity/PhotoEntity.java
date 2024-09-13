package com.example.rent_module.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "photo")
public class PhotoEntity {

    @Id
    @SequenceGenerator(name="photoSequence", sequenceName="photo_sequence", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="photoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "photo_of_apartment")
    private String photoOfApartment;

    public PhotoEntity(String photoOfApartment) {
        this.photoOfApartment = photoOfApartment;
    }
}
