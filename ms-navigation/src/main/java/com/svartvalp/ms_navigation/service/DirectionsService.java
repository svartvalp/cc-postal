package com.svartvalp.ms_navigation.service;

import com.svartvalp.ms_navigation.dto.RouteDto;

import java.util.List;

public interface DirectionsService {
    List<RouteDto> getDirections(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude);
}
