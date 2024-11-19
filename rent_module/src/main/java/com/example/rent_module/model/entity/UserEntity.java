package com.example.rent_module.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_info")
public class UserEntity {

    @Id
    @SequenceGenerator(name = "user_infoSequence", sequenceName = "user_info_sequence", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_infoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

//    @Column(name = "role")
//    private String roles;

    @Column(name = "date_registration")
    private LocalDateTime dateRegistration;

    @Column(name = "token")
    private String token;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
    private List<BookingInfoEntity> bookingInfoEntities;
}
