package com.crashcourse.msdeparture.service;

import com.crashcourse.msdeparture.dto.DepartureDto;
import com.crashcourse.msdeparture.entity.Departure;
import com.crashcourse.msdeparture.repository.AddressRepository;
import com.crashcourse.msdeparture.repository.DepartureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DepartureServiceImplTest {

    @Mock
    private DepartureRepository departureRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private MessageService messageService;
    @Mock
    private DepartureMessageSender departureMessageSender;

    @Test
    void getAllDeparturesByUserId() {
        List<Departure> departures = new ArrayList<>();
        Departure departure1 = new Departure();
        departure1.setNearestUserId(1L);
        Departure departure2 = new Departure();
        departure2.setNearestUserId(2L);
        departures.add(departure1);
        departures.add(departure2);
        Mockito.when(departureRepository.findAllByUserId(Mockito.anyLong())).thenReturn(departures);
        Mockito.when(modelMapper.map(departure1, DepartureDto.class)).thenReturn(new DepartureDto());
        Mockito.when(modelMapper.map(departure2, DepartureDto.class)).thenReturn(new DepartureDto());
        DepartureServiceImpl departureService = new DepartureServiceImpl(departureRepository, addressRepository, modelMapper, messageService, departureMessageSender);
        assertEquals(2L, departureService.getAllDeparturesByUserId(1L).get(1).getAddressee().getId());
    }

    @Test
    void getAllDeparturesByNearestUserId() {
        List<Departure> departures = new ArrayList<>();
        Departure departure1 = new Departure();
        departure1.setNearestUserId(1L);
        Departure departure2 = new Departure();
        departure2.setNearestUserId(2L);
        departures.add(departure1);
        departures.add(departure2);
        Mockito.when(modelMapper.map(departure1, DepartureDto.class)).thenReturn(new DepartureDto());
        Mockito.when(modelMapper.map(departure2, DepartureDto.class)).thenReturn(new DepartureDto());
        Mockito.when(departureRepository.findAllByNearestUserId(Mockito.anyLong())).thenReturn(departures);
        DepartureServiceImpl departureService = new DepartureServiceImpl(departureRepository, addressRepository, modelMapper, messageService, departureMessageSender);
        assertEquals(2L, departureService.getAllDeparturesByNearestUserId(1L).get(1).getAddressee().getId());
    }

    @Test
    void getDepartureById() {
        Departure departure = new Departure();
        DepartureDto departureDto = new DepartureDto();
        departureDto.setId(1L);
        Mockito.when(modelMapper.map(departure, DepartureDto.class)).thenReturn(departureDto);
        Mockito.when(departureRepository.findById(1L)).thenReturn(Optional.of(departure));
        DepartureServiceImpl departureService = new DepartureServiceImpl(departureRepository, addressRepository, modelMapper, messageService, departureMessageSender);
        assertEquals(1L, departureService.getDepartureById(1L).getId());
    }
}