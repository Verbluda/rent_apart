package com.example.rent_module.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
public class ResultsElem {

    @JsonProperty(value = "components")
    private ComponentsObject componentsObject;
}
