package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.PointDto;
import com.crashcourse.ms_navigation.dto.RouteDto;

import java.util.List;

public interface GeoService {
    PointDto getPointByCoordinates(Double longitude, Double latitude);

    List<PointDto> getPointsByName(String name);

    List<RouteDto> getDirections(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude);

    Long getDuration(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude);
}
