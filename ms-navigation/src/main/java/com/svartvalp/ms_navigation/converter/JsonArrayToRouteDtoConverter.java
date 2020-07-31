package com.svartvalp.ms_navigation.converter;

import com.svartvalp.ms_navigation.dto.RouteDto;
import net.minidev.json.JSONArray;
import org.springframework.stereotype.Component;

@Component
public class JsonArrayToRouteDtoConverter extends TypeConverter<JSONArray, RouteDto> {
    @Override
    protected RouteDto convert(JSONArray source) {
        RouteDto route = new RouteDto();
        if (source.size() > 1) {
            route.setLongitude((Double) source.get(0));
            route.setLatitude((Double) source.get(1));
        }
        return route;
    }
}
