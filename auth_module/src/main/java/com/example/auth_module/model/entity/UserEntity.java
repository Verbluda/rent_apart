package com.example.auth_module.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_info")
public class UserEntity {

    @Id
    @SequenceGenerator(name="user_infoSequence", sequenceName="user_info_sequence", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_infoSequence")
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

    public UserEntity(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
//        this.roles = roles;
        this.dateRegistration = LocalDateTime.now();
    }
}
