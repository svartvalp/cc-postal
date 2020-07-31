package com.svartvalp.ms_navigation.service;

import com.svartvalp.ms_navigation.dto.PointDto;
import com.svartvalp.ms_navigation.dto.RouteDto;

import java.util.List;

public interface GeoService {
    PointDto getPointByCoordinates(Double longitude, Double latitude);

    List<PointDto> getPointsByName(String name);

    List<RouteDto> getDirections(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude);
}
