package com.crashcourse.service;

import com.crashcourse.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserKafkaServiceImpl {
    private final KafkaTemplate<String, UserListDto> kafkaStarshipTemplate;
    private final UserService userService;

    @Value("${kafka.topic.user-list.response}")
    private String userTopicIn;

    public void send(UserListDto dto) {
        log.info("send => {}", dto);
        kafkaStarshipTemplate.send(userTopicIn, dto);
    }

    @KafkaListener(
            topics = {"${kafka.topic.user-list.request}"},
            containerFactory = "singleFactory"
    )
    public void consume(UserListDto dto) {
        log.info("=> consumed {}", dto);
        dto.setUsers(userService.getAllUsers());
        send(dto);
    }
}
