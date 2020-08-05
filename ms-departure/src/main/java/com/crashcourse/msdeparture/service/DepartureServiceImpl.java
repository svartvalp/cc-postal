package com.crashcourse.msdeparture.service;

import com.crashcourse.msdeparture.dto.DepartureDto;
import com.crashcourse.msdeparture.entity.Address;
import com.crashcourse.msdeparture.entity.Departure;
import com.crashcourse.msdeparture.exception.DepartureNotFoundException;
import com.crashcourse.msdeparture.repository.AddressRepository;
import com.crashcourse.msdeparture.repository.DepartureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartureServiceImpl implements DepartureService {

    private final DepartureRepository departureRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final MessageService messageService;

    @Transactional
    @Override
    public DepartureDto createDeparture(DepartureDto departureDto) {

        Departure departure = new Departure();
        departure.setId(departureDto.getId());
        departure.setUserId(departureDto.getUserId());

        Double departureLongitude = departureDto.getDeparturePoint().getLongitude();
        Double departureLatitude = departureDto.getDeparturePoint().getLatitude();

        Optional<Address> departureAddressOptional = addressRepository.findByLongitudeAndLatitude(departureLongitude, departureLatitude);
        if (departureAddressOptional.isPresent()) {
            departure.setDeparturePoint(departureAddressOptional.get());
        } else {
            Address newAddress = new Address();
            newAddress.setLongitude(departureLongitude);
            newAddress.setLatitude(departureLatitude);
            newAddress.setAddress(departureDto.getDeparturePoint().getAddress());
            addressRepository.save(newAddress);
            departure.setDeparturePoint(newAddress);
        }

        Double arrivingLongitude = departureDto.getArrivingPoint().getLongitude();
        Double arrivingLatitude = departureDto.getArrivingPoint().getLatitude();

        Optional<Address> arrivingAddressOptional = addressRepository.findByLongitudeAndLatitude(arrivingLongitude, arrivingLatitude);
        if (arrivingAddressOptional.isPresent()) {
            departure.setArrivingPoint(arrivingAddressOptional.get());
        } else {
            Address newAddress = new Address();
            newAddress.setLongitude(arrivingLongitude);
            newAddress.setLatitude(arrivingLatitude);
            newAddress.setAddress(departureDto.getArrivingPoint().getAddress());
            addressRepository.save(newAddress);
            departure.setArrivingPoint(newAddress);
        }

        departure.setType(departureDto.getType());
        departure.setDepartureDate(LocalDateTime.now());
        departure.setArrived(false);
        departure.setWeight(departureDto.getWeight());
        departure.setDescription(departureDto.getDescription());

        departure = departureRepository.save(departure);
        log.info("Create departure with id = {}", departure.getId());

        return modelMapper.map(departure, DepartureDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartureDto> getAllDepartures() {
        List<DepartureDto> departureDtoList =
                StreamSupport.stream(departureRepository.findAll().spliterator(), false)
                .map(m -> modelMapper.map(m, DepartureDto.class))
                .collect(Collectors.toList());

        log.info("Found {} departures", departureDtoList.size());
        return departureDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public DepartureDto getDepartureById(Long id) {
        Optional<Departure> departureOptional = departureRepository.findById(id);
        if (departureOptional.isEmpty()) {
            log.error("Departure with id = {} not found!", id);
            throw new DepartureNotFoundException(String.format(messageService.getMessage("no.such.departure.message"), id));
        }
        Departure departure = departureOptional.get();
        log.info("Found departure with id = {}", id);

        return modelMapper.map(departure, DepartureDto.class);
    }

    @Transactional
    @Override
    public void deleteDeparture(Long id) {
        Optional<Departure> departureOptional = departureRepository.findById(id);
        if (departureOptional.isPresent()) {
            departureRepository.deleteById(id);
            log.info("Removed departure with id = {}", id);
        } else {
            log.error("Departure with id = {} not found!", id);
            throw new DepartureNotFoundException(String.format(messageService.getMessage("no.such.departure.message"), id));
        }
    }
}
