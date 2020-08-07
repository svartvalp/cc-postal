package com.crashcourse.msdeparture.repository;

import com.crashcourse.msdeparture.entity.Departure;
import org.springframework.data.repository.CrudRepository;

public interface DepartureRepository extends CrudRepository<Departure, Long> {
}
