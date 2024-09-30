package com.example.architect_module.model;

import lombok.Data;

import java.util.Map;

@Data
public class ArchitectRequestDto {

    private String tableName;
    private String operation;
    private Map<String, String> values;
}
