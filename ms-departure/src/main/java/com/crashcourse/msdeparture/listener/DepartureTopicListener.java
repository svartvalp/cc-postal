package com.crashcourse.msdeparture.listener;

import com.crashcourse.msdeparture.dto.DepartureDto;
import com.crashcourse.msdeparture.service.DepartureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepartureTopicListener {

    private final DepartureService departureService;

    @KafkaListener(topics = "${kafka.topic.departure.result}", groupId = "${kafka.group_id}", containerFactory = "departureDtoKafkaListenerContainerFactory")
    public void receiveDepartureDtoMessage(DepartureDto departureDto) {
        log.info("Received departure dto: {}.", departureDto);
        departureService.update(departureDto);
    }

}
