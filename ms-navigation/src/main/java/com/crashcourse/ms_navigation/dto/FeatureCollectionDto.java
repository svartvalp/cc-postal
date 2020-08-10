package com.crashcourse.ms_navigation.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeatureCollectionDto {
    private List<String> query;
    private List<FeatureDto> features;
}
