package ru.pkozlov.msdeparture.service;

import ru.pkozlov.msdeparture.dto.DepartureDto;

import java.util.Set;

public interface DepartureService {
    DepartureDto createDeparture(DepartureDto departureDto);
    Set<DepartureDto> getAllDepartures();
    DepartureDto getDepartureById(Long id);
    void deleteDeparture(Long id);
}
