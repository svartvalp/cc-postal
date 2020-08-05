package com.crashcourse.ms_navigation.dto;

import lombok.Data;

@Data
public class PointDto {
    private Double longitude;
    private Double latitude;
    private String placeName;
}
