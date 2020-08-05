package com.crashcourse.ms_navigation.dao;

import com.crashcourse.ms_navigation.entity.Direction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectionDao extends MongoRepository<Direction, String> {
    Direction findByFromLongitudeAndFromLatitudeAndToLongitudeAndToLatitude(Double fromLongitude, Double fromLatitude, Double toLongitude, Double toLatitude);
}
