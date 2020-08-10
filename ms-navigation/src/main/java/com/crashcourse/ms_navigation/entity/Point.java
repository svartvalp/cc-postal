package com.crashcourse.ms_navigation.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@CompoundIndex(name = "coordinates_idx", def = "{'longitude' : 1, 'latitude' : 1}", unique = true)
@Document
@Data
public class Point {

    private Double longitude;
    private Double latitude;
    private String placeName;
}
