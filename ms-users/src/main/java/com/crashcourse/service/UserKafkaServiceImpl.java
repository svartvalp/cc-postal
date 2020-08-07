package com.crashcourse.service;

import com.crashcourse.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserKafkaServiceImpl implements UserKafkaService {
    private final KafkaTemplate<String, UserListDto> kafkaUserTemplate;
    private final UserService userService;

    public void sendUserData(UserListDto dto, String topic) {
        dto.setUsers(userService.getAllUsers());
        log.debug("send => {}", dto);
        kafkaUserTemplate.send(topic, dto);
    }
}