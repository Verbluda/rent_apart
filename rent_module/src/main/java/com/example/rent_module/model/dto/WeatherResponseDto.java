package com.example.rent_module.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class WeatherResponseDto {

    @JsonProperty("cloudiness")
    private String cloudiness;

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("precType")
    private String precType;

    @JsonProperty("precStrength")
    private String precStrength;

    @JsonProperty("pressure")
    private int pressure;

    @JsonProperty("temperature")
    private int temperature;

    @JsonProperty("windSpeed")
    private double windSpeed;

    @JsonProperty("windDirection")
    private String windDirection;
}
