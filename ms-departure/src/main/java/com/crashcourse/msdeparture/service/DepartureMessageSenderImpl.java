package com.crashcourse.msdeparture.service;

import com.crashcourse.msdeparture.dto.DepartureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DepartureMessageSenderImpl implements DepartureMessageSender {

    private final KafkaTemplate<String, DepartureDto> template;

    @Override
    public void sendToTopic(DepartureDto departureDto, String topic) {
        log.info("Send request with body {}", departureDto);
        template.send(topic, departureDto);
    }
}
