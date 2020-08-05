package com.crashcourse.ms_navigation.converter;

import com.crashcourse.ms_navigation.dto.RouteDto;
import net.minidev.json.JSONArray;
import org.springframework.stereotype.Component;

@Component
public class JsonArrayToRouteDtoConverter extends TypeConverter<JSONArray, RouteDto> {
    @Override
    protected RouteDto convert(JSONArray source) {
        RouteDto route = new RouteDto();
        if (source.size() > 1) {
            route.setLongitude(Double.parseDouble(source.get(0).toString()));
            route.setLatitude(Double.parseDouble(source.get(1).toString()));
        }
        return route;
    }
}
