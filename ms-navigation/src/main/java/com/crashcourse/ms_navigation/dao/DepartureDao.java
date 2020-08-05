package com.crashcourse.ms_navigation.dao;

import com.crashcourse.ms_navigation.entity.Departure;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartureDao extends MongoRepository<Departure, String> {
}
