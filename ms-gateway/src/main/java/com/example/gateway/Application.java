package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import java.util.Set;
import java.util.TreeSet;

@EnableZuulProxy
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Set<String> getTokenStorage() {
        return new TreeSet<>();
    }

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter(getTokenStorage());
    }

}
