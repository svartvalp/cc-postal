package com.svartvalp.ms_navigation.converter;

import com.svartvalp.ms_navigation.dto.PointDto;
import com.svartvalp.ms_navigation.entity.Route;
import org.springframework.stereotype.Component;

@Component
public class PointDtoToRouteConverter extends TypeConverter<PointDto, Route> {
    @Override
    protected Route convert(PointDto source) {
        Route route = new Route();
        route.setLatitude(source.getLatitude());
        route.setLongitude(source.getLongitude());
        return route;
    }
}
