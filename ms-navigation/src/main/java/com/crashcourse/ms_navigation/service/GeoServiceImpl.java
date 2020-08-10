package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dao.DirectionDao;
import com.crashcourse.ms_navigation.dao.PointDao;
import com.crashcourse.ms_navigation.dto.PointDto;
import com.crashcourse.ms_navigation.dto.RouteDto;
import com.crashcourse.ms_navigation.entity.Direction;
import com.crashcourse.ms_navigation.entity.Point;
import com.crashcourse.ms_navigation.entity.Route;
import com.crashcourse.ms_navigation.exception.InvalidCoordinateDataException;
import com.crashcourse.ms_navigation.exception.PointNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GeoServiceImpl implements GeoService {

    private final static int MAX_LONGITUDE_DEGREE = 180;
    private final static int MAX_LATITUDE_DEGREE = 90;
    private final GeocodeService geocodeService;
    private final DirectionsService directionsService;
    private final PointDao pointDao;
    private final DirectionDao directionDao;
    private final ModelMapper modelMapper;
    private final MessageService messageService;

    @Override
    public PointDto getPointByCoordinates(Double longitude, Double latitude) {
        log.debug("Finding place for coordinates: ({},{})", longitude, latitude);
        if (Math.abs(longitude) > MAX_LONGITUDE_DEGREE || Math.abs(latitude) > MAX_LATITUDE_DEGREE) {
            throw new InvalidCoordinateDataException(messageService.getMessage("invalid.coordinates.error"));
        }
        Point dbPoint = pointDao.findByLongitudeAndLatitude(longitude, latitude);
        if (dbPoint == null) {
            PointDto pointDto = geocodeService.getPointByCoordinates(longitude, latitude);
            if (pointDto == null) {
                log.info("Not found place for coordinates: ({},{})", longitude, latitude);
                throw new PointNotFoundException(messageService.getMessage("point.not.found.error"));
            }
            dbPoint = modelMapper.map(pointDto, Point.class);
            try {
                pointDao.save(dbPoint);
            } catch (DuplicateKeyException exc) {
                log.debug("Coordinates violate duplicate key exception: {},{}", longitude, latitude);
            }
        }
        log.info("Successfully found place for Point : {}", dbPoint);
        return modelMapper.map(dbPoint, PointDto.class);
    }

    @Override
    public List<PointDto> getPointsByName(String name) {
        return geocodeService.getPointsByName(name);
    }

    @Override
    public List<RouteDto> getDirections(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude) {
        log.debug("Finding directions for coordinates: ({},{}),({},{})", fromLongitude, fromLatitude, toLongitude, toLatitude);
        if (Math.abs(fromLongitude) > MAX_LONGITUDE_DEGREE || Math.abs(toLongitude) > MAX_LONGITUDE_DEGREE
                || Math.abs(toLatitude) > MAX_LATITUDE_DEGREE || Math.abs(fromLatitude) > MAX_LATITUDE_DEGREE) {
            throw new InvalidCoordinateDataException(messageService.getMessage("invalid.coordinates.error"));
        }
        Direction direction = directionDao
                .findByFromLongitudeAndFromLatitudeAndToLongitudeAndToLatitude(fromLongitude, fromLatitude, toLongitude, toLatitude);
        if (direction == null) {
            List<RouteDto> routeDtos = directionsService.getDirections(fromLongitude, fromLatitude, toLongitude, toLatitude);
            List<Route> routes = new ArrayList<>();
            routeDtos.forEach(routeDto -> routes.add(modelMapper.map(routeDto, Route.class)));
            direction = new Direction();
            direction.setFromLatitude(fromLatitude);
            direction.setToLatitude(toLatitude);
            direction.setFromLongitude(fromLongitude);
            direction.setToLongitude(toLongitude);
            direction.setRoutes(routes);
            directionDao.save(direction);
        }
        List<RouteDto> routeDtos = new ArrayList<>();
        if (direction.getRoutes() != null) {
            direction.getRoutes().forEach(route -> routeDtos.add(modelMapper.map(route, RouteDto.class)));
        }
        log.info("Fount {} points for coordinates: ({},{}),({},{})", routeDtos.size(), fromLongitude, fromLatitude, toLongitude, toLatitude);
        return routeDtos;
    }

    @Override
    public Long getDuration(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude) {
        return directionsService.getDuration(fromLongitude, fromLatitude, toLongitude, toLatitude);
    }
}
