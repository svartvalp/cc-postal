package ru.pkozlov.msdeparture.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pkozlov.msdeparture.dto.DepartureDto;
import ru.pkozlov.msdeparture.service.DepartureService;

import java.util.Set;

@RequiredArgsConstructor
@RestController
public class DepartureController {

    private final DepartureService departureService;

    @GetMapping("/departures")
    public Set<DepartureDto> getAllDepartures() {
        return departureService.getAllDepartures();
    }

    @GetMapping("/departure/{id}")
    public DepartureDto getDepartureById(@PathVariable Long id) {
        return departureService.getDepartureById(id);
    }

    @PostMapping("/departure")
    public DepartureDto createDeparture(@RequestBody DepartureDto departureDto) {
        return departureService.createDeparture(departureDto);
    }

    @DeleteMapping("/departure/{id}")
    public void deleteDeparture(@PathVariable Long id) {
        departureService.deleteDeparture(id);
    }
}
