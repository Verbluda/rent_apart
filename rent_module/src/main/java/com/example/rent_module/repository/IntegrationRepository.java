package com.example.rent_module.repository;

import com.example.rent_module.model.entity.IntegrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntegrationRepository extends JpaRepository<IntegrationEntity, String> {
}
