package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.PointDto;

import java.util.List;

public interface GeocodeService {
    PointDto getPointByCoordinates(Double longitude, Double latitude);

    List<PointDto> getPointsByName(String name);
}
