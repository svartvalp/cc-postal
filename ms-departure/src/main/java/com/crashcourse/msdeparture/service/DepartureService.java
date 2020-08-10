package com.crashcourse.msdeparture.service;

import com.crashcourse.msdeparture.dto.DepartureDto;

import java.util.List;

public interface DepartureService {
    DepartureDto createDeparture(DepartureDto departureDto);

    List<DepartureDto> getAllDeparturesByUserId(Long id);

    List<DepartureDto> getAllDeparturesByNearestUserId(Long nearestUserId);

    DepartureDto getDepartureById(Long id);

    void deleteDeparture(Long id);

    void update(DepartureDto departureDto);
}
