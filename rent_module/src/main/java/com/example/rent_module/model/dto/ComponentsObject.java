package com.example.rent_module.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
public class ComponentsObject {

    @JsonProperty(value = "_normalized_city")
    private String normalizedCity;

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "town")
    private String town;
}
