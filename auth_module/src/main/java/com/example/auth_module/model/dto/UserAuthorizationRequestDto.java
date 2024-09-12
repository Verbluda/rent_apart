package com.example.auth_module.model.dto;

import lombok.Data;

@Data
public class UserAuthorizationRequestDto {

    private String email;

    private String password;
}
