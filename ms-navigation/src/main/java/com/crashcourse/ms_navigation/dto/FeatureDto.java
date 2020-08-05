package com.crashcourse.ms_navigation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FeatureDto {
    @JsonProperty(value = "place_name")
    private String placeName;
    private List<Double> center;
}
