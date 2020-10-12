package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dao.DepartureDao;
import com.crashcourse.ms_navigation.dto.AddressDto;
import com.crashcourse.ms_navigation.dto.DepartureDto;
import com.crashcourse.ms_navigation.dto.UserDto;
import com.crashcourse.ms_navigation.dto.UserListDto;
import com.crashcourse.ms_navigation.entity.Address;
import com.crashcourse.ms_navigation.entity.Departure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DepartureServiceImplTest {

    @Mock
    private GeoService geoService;
    @Mock
    private DepartureDao departureDao;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private DepartureMessageSender departureMessageSender;
    @Mock
    private UserListMessageSender userListMessageSender;

    @Test
    void countArrivingDateAndSend() {
        DepartureDto departureDto = new DepartureDto();
        AddressDto departureP = new AddressDto();
        departureP.setAddress("addr");
        departureP.setLatitude(1.0);
        departureP.setLongitude(1.0);
        AddressDto arrivingP = new AddressDto();
        arrivingP.setAddress("addr");
        arrivingP.setLatitude(1.0);
        arrivingP.setLongitude(1.0);
        departureDto.setDeparturePoint(departureP);
        departureDto.setArrivingPoint(arrivingP);
        departureDto.setId(1L);
        departureDto.setDepartureDate(LocalDateTime.of(2000, 1, 1, 1, 1, 1));
        Departure departure = new Departure();
        departure.setGUID("guid");
        Mockito.when(geoService.getDuration(1.0,1.0,1.0,1.0)).thenReturn(60L);
        Mockito.when(departureDao.save(Mockito.any(Departure.class))).thenReturn(departure);
        Mockito.when(modelMapper.map(Mockito.any(DepartureDto.class), Mockito.eq(Departure.class))).thenReturn(departure);
        DepartureServiceImpl departureService = new DepartureServiceImpl(geoService, departureDao, modelMapper, departureMessageSender, userListMessageSender);
        departureService.countArrivingDateAndSend(departureDto);
        assertEquals(2, departureDto.getArrivingDate().getMinute());
    }


    @Test
    void findNearestUserFromUsersAndSend() {

        AddressDto userAddr = new AddressDto();
        userAddr.setLatitude(1.0);
        userAddr.setLongitude(1.0);

        UserListDto userListDto = new UserListDto();
        userListDto.setDepartureId("id");
        List<UserDto> userDtos = new ArrayList<>();
        UserDto user1 = new UserDto();
        user1.setId(1L);
        user1.setAddress(userAddr);
        UserDto user2 = new UserDto();
        user2.setId(2L);
        AddressDto u2Address = new AddressDto();
        u2Address.setLongitude(2.0);
        u2Address.setLatitude(2.0);
        user2.setAddress(u2Address);
        UserDto user3 = new UserDto();
        user3.setId(3L);
        user3.setAddress(userAddr);
        userDtos.add(user1);
        userDtos.add(user2);
        userDtos.add(user3);

        Departure departure = new Departure();
        departure.setGUID("guid");
        Address address = new Address();
        address.setAddress("addr");
        address.setLongitude(1.0);
        address.setLatitude(1.0);
        departure.setArrivingPoint(address);
        departure.setUserId(1L);


        DepartureDto departureDto = new DepartureDto();
        departureDto.setDeparturePoint(userAddr);

        userListDto.setUsers(userDtos);



        Mockito.when(departureDao.findById("id")).thenReturn(Optional.of(departure));
        Mockito.when(modelMapper.map(departure, DepartureDto.class)).thenReturn(departureDto);
        DepartureServiceImpl departureService = new DepartureServiceImpl(geoService, departureDao, modelMapper, departureMessageSender, userListMessageSender);
        departureService.findNearestUserFromUsersAndSend(userListDto);
        assertEquals(3, departureDto.getAddressee().getId());
    }
}