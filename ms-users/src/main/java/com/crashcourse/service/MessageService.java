package com.crashcourse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageSource messageSource;
    private final Locale loc;

    public String getMessage(String msg) {
        try {
            return messageSource.getMessage(msg, null, loc);
        } catch (NoSuchMessageException e) {
            log.error(e.getMessage());
            return "";
        }
    }
}
