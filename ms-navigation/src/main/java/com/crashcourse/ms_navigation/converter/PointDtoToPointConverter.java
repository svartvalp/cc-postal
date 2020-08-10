package com.crashcourse.ms_navigation.converter;

import com.crashcourse.ms_navigation.dto.PointDto;
import com.crashcourse.ms_navigation.entity.Point;
import org.springframework.stereotype.Component;

@Component
public class PointDtoToPointConverter extends TypeConverter<PointDto, Point> {
    @Override
    protected Point convert(PointDto source) {
        Point point = new Point();
        point.setLatitude(source.getLatitude());
        point.setLongitude(source.getLongitude());
        point.setPlaceName(source.getPlaceName());
        return point;
    }
}
