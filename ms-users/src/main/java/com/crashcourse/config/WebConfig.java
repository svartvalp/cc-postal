package com.crashcourse.config;

import com.crashcourse.converter.UserDtoUserEntityConverter;
import com.crashcourse.converter.UserEntityUserDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserDtoUserEntityConverter());
        registry.addConverter(new UserEntityUserDtoConverter());
    }

    @Bean
    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
