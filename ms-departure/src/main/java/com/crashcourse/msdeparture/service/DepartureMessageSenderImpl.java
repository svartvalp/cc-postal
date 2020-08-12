package com.crashcourse.msdeparture.service;

import com.crashcourse.msdeparture.dto.DepartureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DepartureMessageSenderImpl implements DepartureMessageSender {

    private final KafkaTemplate<String, DepartureDto> template;

    @Override
    @Async
    public void sendToTopic(DepartureDto departureDto, String topic) {
        log.info("Send request with body {}", departureDto);
        try {
            template.send(topic, departureDto);
        } catch(Exception e) {
            log.error("Can not send request: {}", e.getMessage());
        }
    }
}
