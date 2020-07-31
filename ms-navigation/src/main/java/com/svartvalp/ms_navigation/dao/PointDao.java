package com.svartvalp.ms_navigation.dao;

import com.svartvalp.ms_navigation.entity.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PointDao extends MongoRepository<Point, String> {
    Optional<Point> findByLongitudeAndLatitude(Double longitude, Double latitude);
}
