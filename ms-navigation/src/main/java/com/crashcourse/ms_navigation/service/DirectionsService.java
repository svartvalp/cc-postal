package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.RouteDto;

import java.util.List;

public interface DirectionsService {
    List<RouteDto> getDirections(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude);

    Long getDuration(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude);
}
