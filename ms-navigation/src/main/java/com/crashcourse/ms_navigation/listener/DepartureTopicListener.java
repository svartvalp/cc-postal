package com.crashcourse.ms_navigation.listener;

import com.crashcourse.ms_navigation.dto.DepartureDto;
import com.crashcourse.ms_navigation.dto.UserListDto;
import com.crashcourse.ms_navigation.service.DepartureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DepartureTopicListener {

    private final DepartureService departureService;

    @KafkaListener(topics = "${kafka.topic.departure.compute}", groupId = "${kafka.group_id}")
    public void receiveDepartureDtoMessage(DepartureDto departureDto) {
        log.debug("Received departure dto: {}. Sending it for counting date", departureDto);
        departureService.countArrivingDateAndSend(departureDto);
    }

    @KafkaListener(topics = "${kafka.topic.user-list.result}", groupId = "${kafka.group_id}")
    public void receiveAllUsersDtoMessage(UserListDto userListDto) {
        log.debug("Received user list with departure id : {}", userListDto.getDepartureId());
        departureService.findNearestUserFromUsersAndSend(userListDto);
    }

}
