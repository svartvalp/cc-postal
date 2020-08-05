package com.crashcourse.ms_navigation.dao;

import com.crashcourse.ms_navigation.entity.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointDao extends MongoRepository<Point, String> {
    Point findByLongitudeAndLatitude(Double longitude, Double latitude);
}
