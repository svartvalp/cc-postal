package com.crashcourse.listener;

import com.crashcourse.dto.UserListDto;
import com.crashcourse.service.UserKafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserListener {
    private final UserKafkaService userKafkaService;

    @Value("${kafka.topic.user-list.response}")
    private String userToNavigation;


    @KafkaListener(
            topics = {"${kafka.topic.user-list.request}"},
            containerFactory = "singleFactory"
    )
    public void consume(UserListDto dto) {
        log.info("consumed {}", dto);
        userKafkaService.sendUserData(dto, userToNavigation);
    }
}
