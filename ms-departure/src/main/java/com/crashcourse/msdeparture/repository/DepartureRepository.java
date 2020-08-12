package com.crashcourse.msdeparture.repository;

import com.crashcourse.msdeparture.entity.Departure;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DepartureRepository extends CrudRepository<Departure, Long> {

    List<Departure> findAllByUserId(Long userId);

    List<Departure> findAllByNearestUserId(Long nearestUserId);

    List<Departure> findAllByArrivingDate(LocalDateTime arrivingDate);
}
