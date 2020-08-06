package com.crashcourse.msdeparture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageSource messageSource;
    private final Locale locale;

    public String getMessage(String message, Object... args) {
        try {
            return messageSource.getMessage(message, args, locale);
        } catch (NoSuchMessageException exc) {
            log.error(exc.getMessage());
            return "";
        }
    }
}
