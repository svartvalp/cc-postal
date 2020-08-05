package com.crashcourse.ms_navigation.converter;

import com.crashcourse.ms_navigation.dto.DepartureDto;
import com.crashcourse.ms_navigation.entity.Address;
import com.crashcourse.ms_navigation.entity.Departure;
import org.springframework.stereotype.Component;

@Component
public class DepartureDtoToDepartureConverter extends TypeConverter<DepartureDto, Departure> {
    @Override
    protected Departure convert(DepartureDto source) {
        Departure departure = new Departure();
        departure.setDepartureId(source.getId());
        departure.setUserId(source.getUserId());

        Address departurePoint = new Address();
        departurePoint.setAddress(source.getDeparturePoint().getAddress());
        departurePoint.setLongitude(source.getDeparturePoint().getLongitude());
        departurePoint.setLatitude(source.getDeparturePoint().getLatitude());
        departure.setDeparturePoint(departurePoint);

        Address arrivingPoint = new Address();
        arrivingPoint.setAddress(source.getArrivingPoint().getAddress());
        arrivingPoint.setLongitude(source.getArrivingPoint().getLongitude());
        arrivingPoint.setLatitude(source.getArrivingPoint().getLatitude());
        departure.setArrivingPoint(arrivingPoint);

        departure.setDepartureDate(source.getDepartureDate());
        departure.setArrivingDate(source.getArrivingDate());
        return departure;
    }
}
