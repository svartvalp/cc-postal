package com.crashcourse.ms_navigation.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Properties;

@EnableMongoRepositories(basePackages = "com.crashcourse.ms_navigation.dao")
@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Properties messages() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("messages.properties"));
        return properties;
    }
}
