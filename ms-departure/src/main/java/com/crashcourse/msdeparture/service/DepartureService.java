package com.crashcourse.msdeparture.service;

import com.crashcourse.msdeparture.dto.DepartureDto;

import java.util.List;

public interface DepartureService {
    DepartureDto createDeparture(DepartureDto departureDto);

    List<DepartureDto> getAllDepartures();

    DepartureDto getDepartureById(Long id);

    void deleteDeparture(Long id);
}
