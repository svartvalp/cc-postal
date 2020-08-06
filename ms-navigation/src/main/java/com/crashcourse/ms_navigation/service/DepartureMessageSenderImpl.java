package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.DepartureDto;
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
        template.send(topic, departureDto);
    }
}
