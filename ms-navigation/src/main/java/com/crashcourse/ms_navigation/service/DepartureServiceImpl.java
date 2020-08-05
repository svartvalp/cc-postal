package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dao.DepartureDao;
import com.crashcourse.ms_navigation.dto.DepartureDto;
import com.crashcourse.ms_navigation.dto.UserDto;
import com.crashcourse.ms_navigation.dto.UserListDto;
import com.crashcourse.ms_navigation.entity.Departure;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TreeMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartureServiceImpl implements DepartureService {

    private final GeoService geoService;
    private final DepartureDao departureDao;
    private final ModelMapper modelMapper;
    private final DepartureMessageSender departureMessageSender;
    private final UserListMessageSender userListMessageSender;

    @Value("${kafka.topic.departure.result}")
    private String departureResult;
    @Value("${kafka.topic.user-list.request}")
    private String userListRequest;


    @Override
    public void countArrivingDateAndSend(DepartureDto departureDto) {
        if (departureDto.getDeparturePoint() != null
                && departureDto.getArrivingPoint() != null
                && departureDto.getDepartureDate() != null) {
            LocalDateTime departureDate = departureDto.getDepartureDate();
            log.debug("Departure date for dto is {}", departureDate);
            Long trackTime = geoService
                    .getDuration(departureDto.getDeparturePoint().getLongitude(),
                            departureDto.getDeparturePoint().getLatitude(),
                            departureDto.getArrivingPoint().getLongitude(),
                            departureDto.getArrivingPoint().getLatitude());
            LocalDateTime arrivingDate = null;
            if(trackTime != null) {
                arrivingDate =  departureDate.plusSeconds(trackTime);
            }
            log.debug("Arriving date for dto is {}", arrivingDate);
            departureDto.setArrivingDate(arrivingDate);
             var savedDeparture = saveDeparture(departureDto);
            UserListDto userListDto = new UserListDto();
            userListDto.setDepartureId(savedDeparture.getGUID());
            log.debug("Sending userList with departure id : {}", userListDto.getDepartureId());
            log.info("Counted arriving date for dto. Departure date is : {}, arriving date is {}",
                    departureDto.getDepartureDate(), departureDto.getArrivingDate());
            userListMessageSender.sendToTopic(userListDto, userListRequest);
        }
    }

    @Override
    public Departure saveDeparture(DepartureDto departureDto) {
        return departureDao.save(modelMapper.map(departureDto, Departure.class));
    }

    @Override
    public void findNearestUserFromUsersAndSend(UserListDto userListDto) {
        if (userListDto != null) {
            log.debug("Trying to find nearest user for departure with id : {}", userListDto.getDepartureId());
            Optional<Departure> departure = departureDao.findById(userListDto.getDepartureId());
            if (departure.isPresent()) {
                log.debug("Departure was found in database, departure user id is {}", departure.get().getDepartureId());
                double longitude = departure.get().getArrivingPoint().getLongitude();
                double latitude = departure.get().getArrivingPoint().getLatitude();
                log.debug("Departure longitude and latitude are ({},{})", longitude, latitude);
                DepartureDto departureDto = modelMapper.map(departure.get(), DepartureDto.class);
                TreeMap<Double, UserDto> usersMap = new TreeMap<>();
                userListDto.getUsers().forEach(user -> {
                    if(user.getId() != departure.get().getUserId()) {
                        usersMap.put(Math.sqrt(Math.pow(user.getUserAddress().getLongitude() - longitude, 2)
                                + Math.pow(user.getUserAddress().getLatitude() - latitude, 2)), user);
                    }
                });
                UserDto nearestUser = usersMap.firstEntry().getValue();
                log.debug("Nearest user\'s coordinates are ({},{})",
                        nearestUser.getUserAddress().getLongitude(),
                        nearestUser.getUserAddress().getLatitude());
                departureDto.setNearestUser(nearestUser);
                departureDao.deleteById(departure.get().getGUID());
                log.info("Nearest user with id {} and coordinates ({},{}) was found for departure id {}",
                        nearestUser.getId(),
                        nearestUser.getUserAddress().getLongitude(),
                        nearestUser.getUserAddress().getLatitude(),
                        departure.get().getDepartureId());
                departureMessageSender.sendToTopic(departureDto, departureResult);
            } else {
                log.warn("Departure in database was not found");
            }
        } else {
            log.warn("UserListDto is empty, can't process it");
        }
    }
}
