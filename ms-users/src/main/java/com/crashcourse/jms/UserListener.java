package com.crashcourse.jms;

import com.crashcourse.dto.UserDto;
import com.crashcourse.exception.NoSuchEntityException;
import com.crashcourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserListener {
    private final UserService userService;

    @JmsListener(destination = "ms_users", containerFactory = "myFactory")
    @SendTo("ms_departure")
    public UserDto receiveMessage(Integer id) {
        try {
            return userService.getUserById(id);
        } catch (NoSuchEntityException var3) {
            return null;
        }
    }
}
