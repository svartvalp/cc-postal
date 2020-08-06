package com.crashcourse.ms_navigation.converter;

import com.crashcourse.ms_navigation.dto.RouteDto;
import com.crashcourse.ms_navigation.entity.Route;
import org.springframework.stereotype.Component;

@Component
public class RouteDtoToRouteConverter extends TypeConverter<RouteDto, Route> {

    @Override
    protected Route convert(RouteDto source) {
        Route route = new Route();
        route.setLongitude(source.getLongitude());
        route.setLatitude(source.getLatitude());
        return route;
    }
}
