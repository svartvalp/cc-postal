package com.svartvalp.ms_navigation.converter;

import com.svartvalp.ms_navigation.dto.FeatureDto;
import com.svartvalp.ms_navigation.dto.PointDto;
import org.springframework.stereotype.Component;

@Component
public class FeatureDtoToPointDtoConverter extends TypeConverter<FeatureDto, PointDto> {
    @Override
    protected PointDto convert(FeatureDto source) {
        PointDto pointDto = new PointDto();
        pointDto.setPlaceName(source.getPlaceName());
        if (source.getCenter() != null) {
            pointDto.setLongitude(source.getCenter().get(0));
            pointDto.setLatitude(source.getCenter().get(1));
        }
        return pointDto;
    }
}
