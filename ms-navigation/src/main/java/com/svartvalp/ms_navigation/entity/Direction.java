package com.svartvalp.ms_navigation.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@CompoundIndex(name = "coordinates_idx", def = "{'fromLongitude' : 1, 'fromLatitude' : 1, 'toLongitude' : 1, 'toLatitude' : 1}")
@Document
@Data
public class Direction {

    private String id;

    private Double fromLongitude;

    private Double fromLatitude;

    private Double toLongitude;

    private Double toLatitude;

    private List<Route> routes;
}
