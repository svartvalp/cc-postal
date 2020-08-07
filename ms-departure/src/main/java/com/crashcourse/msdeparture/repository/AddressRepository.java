package com.crashcourse.msdeparture.repository;

import com.crashcourse.msdeparture.entity.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Optional<Address> findByLongitudeAndLatitude(Double longitude, Double latitude);
}
