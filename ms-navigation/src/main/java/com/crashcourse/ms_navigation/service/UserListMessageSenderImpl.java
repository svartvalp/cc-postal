package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserListMessageSenderImpl implements UserListMessageSender {

    private final KafkaTemplate<String, UserListDto> kafkaTemplate;

    @Override
    public void sendToTopic(UserListDto userListDto, String topic) {
        kafkaTemplate.send(topic, userListDto);
    }
}
