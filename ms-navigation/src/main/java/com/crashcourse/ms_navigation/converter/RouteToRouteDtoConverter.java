package com.crashcourse.ms_navigation.converter;

import com.crashcourse.ms_navigation.dto.RouteDto;
import com.crashcourse.ms_navigation.entity.Route;
import org.springframework.stereotype.Component;

@Component
public class RouteToRouteDtoConverter extends TypeConverter<Route, RouteDto> {
    @Override
    protected RouteDto convert(Route source) {
        RouteDto routeDto = new RouteDto();
        routeDto.setLongitude(source.getLongitude());
        routeDto.setLatitude(source.getLatitude());
        return routeDto;
    }
}
