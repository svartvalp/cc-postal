package com.crashcourse.ms_navigation.converter;

import com.crashcourse.ms_navigation.dto.AddressDto;
import com.crashcourse.ms_navigation.dto.DepartureDto;
import com.crashcourse.ms_navigation.entity.Departure;
import org.springframework.stereotype.Component;

@Component
public class DepartureToDepartureDtoConverter extends TypeConverter<Departure, DepartureDto> {
    @Override
    protected DepartureDto convert(Departure source) {
        DepartureDto departureDto = new DepartureDto();
        departureDto.setId(source.getDepartureId());
        departureDto.setUserId(source.getUserId());
        departureDto.setDepartureDate(source.getDepartureDate());
        departureDto.setArrivingDate(source.getArrivingDate());

        AddressDto departure = new AddressDto();
        departure.setAddress(source.getDeparturePoint().getAddress());
        departure.setLongitude(source.getDeparturePoint().getLongitude());
        departure.setLatitude(source.getDeparturePoint().getLatitude());
        departureDto.setDeparturePoint(departure);

        AddressDto arriving = new AddressDto();
        arriving.setAddress(source.getArrivingPoint().getAddress());
        arriving.setLongitude(source.getArrivingPoint().getLongitude());
        arriving.setLatitude(source.getArrivingPoint().getLatitude());
        departureDto.setArrivingPoint(arriving);
        return departureDto;
    }
}
