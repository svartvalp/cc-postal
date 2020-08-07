package com.crashcourse.db.repository;

import com.crashcourse.db.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
    Optional<AddressEntity> findByAddress(String address);
}
