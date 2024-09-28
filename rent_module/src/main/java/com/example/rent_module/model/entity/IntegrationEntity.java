package com.example.rent_module.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "integration_info")
public class IntegrationEntity {

    @Id
    @Column(name = "service_name_id")
    private String serviceNameId;

    @Column(name = "path_value")
    private String pathValue;

    @Column(name = "token_value")
    private String tokenValue;
}
