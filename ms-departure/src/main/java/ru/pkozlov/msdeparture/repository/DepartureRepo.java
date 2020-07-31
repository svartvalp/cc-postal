package ru.pkozlov.msdeparture.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pkozlov.msdeparture.entity.Departure;

public interface DepartureRepo extends CrudRepository<Departure, Long> {
}
