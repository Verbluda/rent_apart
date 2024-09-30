package com.example.rent_module.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
public class GeoCoderResponseDto {

    @JsonProperty(value = "results")
    private List<ResultsElem> resultsElemList;
}
