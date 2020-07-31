package com.svartvalp.ms_navigation.converter;

import com.svartvalp.ms_navigation.dto.PointDto;
import com.svartvalp.ms_navigation.entity.Point;
import org.springframework.stereotype.Component;

@Component
public class PointToPointDtoConverter extends TypeConverter<Point, PointDto> {
    @Override
    protected PointDto convert(Point source) {
        PointDto pointDto = new PointDto();
        pointDto.setLatitude(source.getLatitude());
        pointDto.setLongitude(source.getLongitude());
        pointDto.setPlaceName(source.getPlaceName());
        return pointDto;
    }
}
