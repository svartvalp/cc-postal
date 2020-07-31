package com.svartvalp.ms_navigation.controller;

import com.svartvalp.ms_navigation.dto.PointDto;
import com.svartvalp.ms_navigation.dto.RouteDto;
import com.svartvalp.ms_navigation.service.GeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DirectionController {

    private final GeoService geoService;

    @GetMapping(value = "/geocoding/point", produces = MediaType.APPLICATION_JSON_VALUE)
    public PointDto getPointByCoordinates(@RequestParam("longitude") Double longitude, @RequestParam("latitude") Double latitude) {
        return geoService.getPointByCoordinates(longitude, latitude);
    }

    @GetMapping(value = "/geocoding/places", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PointDto> getPointsByQuery(@RequestParam("query") String query) {
        return geoService.getPointsByName(query);
    }

    @GetMapping(value = "/direction", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RouteDto> getDirections(@RequestParam("from_latitude") Double fromLatitude,
                                        @RequestParam("from_longitude") Double fromLongitude,
                                        @RequestParam("to_longitude") Double toLongitude,
                                        @RequestParam("to_latitude") Double toLatitude) {
        return geoService.getDirections(fromLongitude, fromLatitude, toLongitude, toLatitude);
    }

}
