package com.example.rent_module.model.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
public class ConditionObject {

    @JsonProperty(value = "text")
    private String weatherCondition;

    @JsonProperty(value = "icon")
    private String weatherIcon;
}
