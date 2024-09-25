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
    @SequenceGenerator(name="photoSequence", sequenceName="photo_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="photoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "photo_of_apartment")
    private byte[] photoOfApartment;

    public PhotoEntity(byte[] photoOfApartment) {
        this.photoOfApartment = photoOfApartment;
    }

    @Override
    public String toString() {
        return "PhotoEntity{" +
                "id=" + id +
                '}';
    }
}
