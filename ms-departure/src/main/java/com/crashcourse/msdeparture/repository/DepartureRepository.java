package com.crashcourse.msdeparture.repository;

import org.springframework.data.repository.CrudRepository;
import com.crashcourse.msdeparture.entity.Departure;

public interface DepartureRepository extends CrudRepository<Departure, Long> {
}
