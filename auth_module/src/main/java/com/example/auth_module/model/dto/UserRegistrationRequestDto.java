package com.example.auth_module.model.dto;

import lombok.Data;

@Data
public class UserRegistrationRequestDto {

    private String userName;

    private String email;

    private String password;

//    private Role roles;
}
