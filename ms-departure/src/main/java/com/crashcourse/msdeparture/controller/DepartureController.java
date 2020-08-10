package com.crashcourse.msdeparture.controller;

import com.crashcourse.msdeparture.dto.DepartureDto;
import com.crashcourse.msdeparture.service.DepartureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DepartureController {

    private final DepartureService departureService;

    @GetMapping("/departure/list")
    public List<DepartureDto> getAllDepartures() {
        return departureService.getAllDepartures();
    }

    @GetMapping("/departure/{id}")
    public DepartureDto getDepartureById(@PathVariable Long id) {
        return departureService.getDepartureById(id);
    }

    @PostMapping("/departure")
    public DepartureDto createDeparture(@RequestBody @Valid DepartureDto departureDto) {
        return departureService.createDeparture(departureDto);
    }

    @DeleteMapping("/departure/{id}")
    public void deleteDeparture(@PathVariable Long id) {
        departureService.deleteDeparture(id);
    }
}
