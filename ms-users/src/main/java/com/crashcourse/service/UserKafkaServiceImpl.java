package com.crashcourse.service;

import com.crashcourse.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserKafkaServiceImpl implements UserKafkaService {
    private final KafkaTemplate<String, UserListDto> kafkaStarshipTemplate;

    @Value("${kafka.topic.user-list.response}")
    private String userTopicIn;

    public void send(UserListDto dto) {
        log.info("send => {}", dto);
        kafkaStarshipTemplate.send(userTopicIn, dto);
    }
}