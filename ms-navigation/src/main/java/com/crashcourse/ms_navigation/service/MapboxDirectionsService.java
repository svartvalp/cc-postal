package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.RouteDto;
import com.crashcourse.ms_navigation.exception.MaximumDistanceLimitationException;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MapboxDirectionsService implements DirectionsService {

    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final MessageService messageService;

    @Value("${api.directions}")
    private String directionsApiUrl;
    @Value("${api.access_token}")
    private String accessToken;

    @Override
    public List<RouteDto> getDirections(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude) {
        log.debug("Finding directions by API Mapbox for coordinates : ({},{}), ({},{})", fromLongitude, fromLatitude, toLongitude, toLatitude);
        String coordinates = fromLongitude + "," + fromLatitude + ";" + toLongitude + "," + toLatitude;
        String url = directionsApiUrl + coordinates + "?access_token=" + accessToken + "&geometries=geojson&steps=true";
        try {
            String body = restTemplate.getForEntity(url, String.class).getBody();
            String jsonPath = "$.routes[*].legs[*].steps[*].intersections[*].location";
            List<JSONArray> result = JsonPath.read(body, jsonPath);
            List<RouteDto> routeDtos = new ArrayList<>();
            log.debug("Found {} points for direction", routeDtos.size());
            result.forEach(arr -> routeDtos.add(modelMapper.map(arr, RouteDto.class)));
            return routeDtos;
        } catch (HttpClientErrorException exc) {
            log.debug("Client error with message: {}", exc.getLocalizedMessage());
            throw new MaximumDistanceLimitationException(messageService.getMessage("maximum.distance.limit.error"));
        }
    }

    @Override
    public Long getDuration(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude) {
        log.info("Finding duration by API Mapbox for coordinates : ({},{}), ({},{})", fromLongitude, fromLatitude, toLongitude, toLatitude);
        String coordinates = fromLongitude + "," + fromLatitude + ";" + toLongitude + "," + toLatitude;
        String url = directionsApiUrl + coordinates + "?access_token=" + accessToken + "&geometries=geojson&steps=true";
        try {
            String body = restTemplate.getForEntity(url, String.class).getBody();
            String jsonPath = "$.routes[*].duration";
            JSONArray result = JsonPath.read(body, jsonPath);
            Double summ = 0D;
            for (var obj : result) {
                summ += Double.parseDouble(obj.toString());
            }
            return summ.longValue();
        } catch (HttpClientErrorException exc) {
            log.debug("Client error with message: {}", exc.getLocalizedMessage());
            throw new MaximumDistanceLimitationException(messageService.getMessage("maximum.distance.limit.error"));
        }
    }
}
