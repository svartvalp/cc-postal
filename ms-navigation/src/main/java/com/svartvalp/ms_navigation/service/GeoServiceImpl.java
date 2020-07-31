package com.svartvalp.ms_navigation.service;

import com.svartvalp.ms_navigation.dao.DirectionDao;
import com.svartvalp.ms_navigation.dao.PointDao;
import com.svartvalp.ms_navigation.dto.PointDto;
import com.svartvalp.ms_navigation.dto.RouteDto;
import com.svartvalp.ms_navigation.entity.Direction;
import com.svartvalp.ms_navigation.entity.Point;
import com.svartvalp.ms_navigation.entity.Route;
import com.svartvalp.ms_navigation.exception.InvalidCoordinateDataException;
import com.svartvalp.ms_navigation.exception.PointNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Point> dbPoint = pointDao.findByLongitudeAndLatitude(longitude, latitude);
        if (dbPoint.isEmpty()) {
            PointDto pointDto = geocodeService.getPointByCoordinates(longitude, latitude);
            if (pointDto == null) {
                log.info("Not found place for coordinates: ({},{})", longitude, latitude);
                throw new PointNotFoundException(messageService.getMessage("point.not.found.error"));
            }
            dbPoint = Optional.of(modelMapper.map(pointDto, Point.class));
            pointDao.save(dbPoint.get());
        }
        log.info("Successfully found place for Point : {}", dbPoint.get());
        return modelMapper.map(dbPoint.get(), PointDto.class);
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
}
