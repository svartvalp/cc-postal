package com.crashcourse.msdeparture.service;

import com.crashcourse.msdeparture.dto.AddressDto;
import com.crashcourse.msdeparture.dto.DepartureDto;
import com.crashcourse.msdeparture.dto.UserDto;
import com.crashcourse.msdeparture.entity.Address;
import com.crashcourse.msdeparture.entity.Departure;
import com.crashcourse.msdeparture.exception.DepartureNotFoundException;
import com.crashcourse.msdeparture.repository.AddressRepository;
import com.crashcourse.msdeparture.repository.DepartureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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
    private final DepartureMessageSender departureMessageSender;

    @Value("${kafka.topic.departure.compute}")
    private String departureRequest;

    @Transactional
    @Override
    public DepartureDto createDeparture(DepartureDto departureDto) {

        Departure departure = new Departure();
        departure.setId(departureDto.getId());
        departure.setUserId(departureDto.getUserId());

        AddressDto departurePoint = departureDto.getDeparturePoint();
        Double departureLongitude = departurePoint.getLongitude();
        Double departureLatitude = departurePoint.getLatitude();

        Optional<Address> departureAddressOptional;
        if (departurePoint.getId() != null) {
            departureAddressOptional = addressRepository.findById(departurePoint.getId());
        } else {
            departureAddressOptional = addressRepository.findByLongitudeAndLatitude(departureLongitude, departureLatitude);
        }

        if (departureAddressOptional.isPresent()) {
            departure.setDeparturePoint(departureAddressOptional.get());
        } else {
            Address newAddress = new Address();
            newAddress.setLongitude(departureLongitude);
            newAddress.setLatitude(departureLatitude);
            newAddress.setAddress(departurePoint.getAddress());
            addressRepository.save(newAddress);
            departure.setDeparturePoint(newAddress);
        }

        AddressDto arrivingPoint = departureDto.getArrivingPoint();
        Double arrivingLongitude = arrivingPoint.getLongitude();
        Double arrivingLatitude = arrivingPoint.getLatitude();

        Optional<Address> arrivingAddressOptional;
        if (arrivingPoint.getId() != null) {
            arrivingAddressOptional = addressRepository.findById(arrivingPoint.getId());
        } else {
            arrivingAddressOptional = addressRepository.findByLongitudeAndLatitude(arrivingLongitude, arrivingLatitude);
        }

        if (arrivingAddressOptional.isPresent()) {
            departure.setArrivingPoint(arrivingAddressOptional.get());
        } else {
            Address newAddress = new Address();
            newAddress.setLongitude(arrivingLongitude);
            newAddress.setLatitude(arrivingLatitude);
            newAddress.setAddress(arrivingPoint.getAddress());
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

        DepartureDto newDeparture = modelMapper.map(departure, DepartureDto.class);
        departureMessageSender.sendToTopic(newDeparture, departureRequest);

        return newDeparture;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartureDto> getAllDeparturesByUserId(Long id) {
        List<DepartureDto> departureDtoList =
                departureRepository.findAllByUserId(id).stream()
                        .map(m -> {
                            DepartureDto departureDto = modelMapper.map(m, DepartureDto.class);
                            UserDto userDto = new UserDto();
                            userDto.setId(m.getNearestUserId());
                            departureDto.setAddressee(userDto);
                            return departureDto;
                        })
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
            throw new DepartureNotFoundException(messageService.getMessage("no.such.departure.message", id));
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
            throw new DepartureNotFoundException(messageService.getMessage("no.such.departure.message", id));
        }
    }

    @Transactional
    @Override
    public void update(DepartureDto departureDto) {
        Optional<Departure> departureOptional = departureRepository.findById(departureDto.getId());
        if (departureOptional.isPresent()) {
            Departure departure = departureOptional.get();
            if (departureDto.getAddressee() != null) {
                departure.setNearestUserId(departureDto.getAddressee().getId());
            } else {
                departure.setNearestUserId(null);
            }
            departure.setArrivingDate(departureDto.getArrivingDate());

            departure = departureRepository.save(departure);
            log.info("Updated departure with id = {}", departure.getId());
        }
    }
}
