package com.svartvalp.ms_navigation.converter;

import com.svartvalp.ms_navigation.dto.RouteDto;
import net.minidev.json.JSONArray;
import org.springframework.stereotype.Component;

@Component
public class JsonArrayToRouteDtoConverter extends TypeConverter<JSONArray, RouteDto> {
    @Override
    protected RouteDto convert(JSONArray source) {
        RouteDto route = new RouteDto();
        if (source != null) {
            route.setLongitude(source.size() > 0 ? (Double) source.get(0) : null);
            route.setLatitude(source.size() > 1 ? (Double) source.get(1) : null);
        }
        return route;
    }
}
