package com.crashcourse.ms_navigation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Properties;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final Properties messages;

    @Override
    public String getMessage(String property) {
        return messages.getProperty(property);
    }
}
