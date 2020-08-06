package com.crashcourse.msdeparture.repository;

import org.springframework.data.repository.CrudRepository;
import com.crashcourse.msdeparture.entity.Address;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    Optional<Address> findByLongitudeAndLatitude(Double longitude, Double latitude);
}
