package com.crashcourse.ms_navigation.configuration;

import com.crashcourse.ms_navigation.dto.AddressDto;
import com.crashcourse.ms_navigation.dto.DepartureDto;
import com.crashcourse.ms_navigation.dto.UserDto;
import com.crashcourse.ms_navigation.dto.UserListDto;
import com.crashcourse.ms_navigation.service.UserListMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class Scheduler {

    private final KafkaTemplate<String, DepartureDto> template;
    private final UserListMessageSender userListMessageSender;
    @Value("${kafka.topic.departure.compute}")
    String topic;

    @Scheduled(initialDelay = 0, fixedDelay = 10000)
    public void send() {
        DepartureDto departureDto = new DepartureDto();
        AddressDto departure = new AddressDto();
        AddressDto arriving = new AddressDto();
        departure.setLongitude(37.241958D);
        departure.setLatitude(55.593704D);
        arriving.setLongitude(37.287383);
        arriving.setLatitude(55.59459);
        departureDto.setDeparturePoint(departure);
        departureDto.setArrivingPoint(arriving);
        departureDto.setDepartureDate(LocalDateTime.now());
        departureDto.setId((long) (Math.random()*100));
        departureDto.setUserId((long) (Math.random()*100));
        template.send(topic, departureDto);
    }


    @KafkaListener(topics = "${kafka.topic.user-list.request}", groupId = "${kafka.group_id}")
    public void hanlde(UserListDto userListDto) {
        log.info("got it! {}", userListDto);
        UserDto user1 = new UserDto();
        user1.setId(1L);
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("dadkjlsa");
        addressDto.setLatitude(3.0);
        addressDto.setLongitude(2.1);
        user1.setUserAddress(addressDto);
        userListDto.setUsers(List.of(user1));
        userListMessageSender.sendToTopic(userListDto, "user-list_result");
    }

    @KafkaListener(topics = "${kafka.topic.departure.result}", groupId = "${kafka.group_id}")
    public void listen(DepartureDto departureDto) {
        log.info("got it! Successfully returned out : {}", departureDto.toString());
    }
}
