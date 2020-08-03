package com.crashcourse.config;

import com.crashcourse.converter.UserDtoUserEntityConverter;
import com.crashcourse.converter.UserEntityUserDtoConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserDtoUserEntityConverter());
        registry.addConverter(new UserEntityUserDtoConverter());
    }
}
