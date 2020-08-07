package com.crashcourse.listener;

import com.crashcourse.dto.UserListDto;
import com.crashcourse.service.UserKafkaService;
import com.crashcourse.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserListener {

    private UserService userService;
    private UserKafkaService userKafkaService;

    @KafkaListener(
            topics = {"${kafka.topic.user-list.request}"},
            containerFactory = "singleFactory"
    )
    public void consume(UserListDto dto) {
        log.info("=> consumed {}", dto);
        dto.setUsers(userService.getAllUsers());
        userKafkaService.send(dto);
    }
}
