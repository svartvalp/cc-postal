package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.FeatureCollectionDto;
import com.crashcourse.ms_navigation.dto.FeatureDto;
import com.crashcourse.ms_navigation.dto.PointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MapboxGeocodeService implements GeocodeService {
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    @Value("${api.geocoding}")
    private String geocodingApiUrl;
    @Value("${api.access_token}")
    private String accessToken;

    @Override
    public PointDto getPointByCoordinates(Double longitude, Double latitude) {
        log.debug("Finding place using Mapbox API for coordinates: ({},{})", longitude, latitude);
        String url = geocodingApiUrl + longitude + "," + latitude + ".json?access_token=" + accessToken;
        log.debug("Url for request is : {}", url);
        var featureCollectionDto = restTemplate
                .getForEntity(url, FeatureCollectionDto.class).getBody();
        var features = featureCollectionDto != null ? featureCollectionDto.getFeatures() : new ArrayList<FeatureDto>();
        log.debug("Found {} points from request", features.size());
        return features.size() > 0
                ? modelMapper.map(features.get(0), PointDto.class) : null;
    }

    @Override
    public List<PointDto> getPointsByName(String name) {
        log.debug("Finding points by query: {}", name);
        String url = geocodingApiUrl + name + ".json?access_token=" + accessToken;
        log.debug("Url for request is : {}", url);
        var featureCollectionDto = restTemplate.getForEntity(url, FeatureCollectionDto.class).getBody();
        List<PointDto> pointDtos = new ArrayList<>();
        if (featureCollectionDto != null) {
            var features = featureCollectionDto.getFeatures();
            log.debug("Found {} points for request", features.size());
            for (var feature : features) {
                pointDtos.add(modelMapper.map(feature, PointDto.class));
            }
        }
        return pointDtos;
    }
}
