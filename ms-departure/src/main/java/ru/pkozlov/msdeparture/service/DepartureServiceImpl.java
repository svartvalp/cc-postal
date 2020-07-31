package ru.pkozlov.msdeparture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pkozlov.msdeparture.dto.DepartureDto;
import ru.pkozlov.msdeparture.entity.Departure;
import ru.pkozlov.msdeparture.exception.DepartureNotFoundException;
import ru.pkozlov.msdeparture.repository.DepartureRepo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartureServiceImpl implements DepartureService {

    private final DepartureRepo departureRepo;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public DepartureDto createDeparture(DepartureDto departureDto) {

        Departure departure = new Departure();
        departure.setPK_id(departureDto.getPK_id());
        departure.setUser_id(departureDto.getUser_id());
        departure.setDeparture_point_id(departureDto.getDeparture_point_id());
        departure.setArriving_point_id(departureDto.getArriving_point_id());
        departure.setType(departureDto.getType());
        departure.setDeparture_date(LocalDateTime.now());
        departure.setArrived(false);

        departure = departureRepo.save(departure);
        log.info("Create departure with id = {}", departure.getPK_id());

        return modelMapper.map(departure, DepartureDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<DepartureDto> getAllDepartures() {
        Set<DepartureDto> departureDtos = new HashSet<>();
        for (Departure d : departureRepo.findAll()) {
            departureDtos.add(modelMapper.map(d, DepartureDto.class));
        }
        log.info("Found {} departures", departureDtos.size());
        return departureDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public DepartureDto getDepartureById(Long id) {
        Optional<Departure> departureOptional = departureRepo.findById(id);
        if(!departureOptional.isPresent()) {
            log.error("Departure with id = {} not found!", id);
            throw new DepartureNotFoundException(String.format("Departure with id = %d not found!", id));
        }
        Departure departure = departureOptional.get();
        log.info("Found departure with id = {}", id);

        return modelMapper.map(departure, DepartureDto.class);
    }

    @Override
    public void deleteDeparture(Long id) {
        Optional<Departure> departureOptional = departureRepo.findById(id);
        if (departureOptional.isPresent()) {
            departureRepo.deleteById(id);
            log.info("Removed departure with id = {}", id);
        } else {
            log.error("Departure with id = {} not found", id);
            throw new DepartureNotFoundException(String.format("Departure with id = %d not found", id));
        }

    }


}
