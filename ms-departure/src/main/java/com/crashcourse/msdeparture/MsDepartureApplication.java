package com.crashcourse.msdeparture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableKafka
@EnableAsync
@EnableScheduling
public class MsDepartureApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDepartureApplication.class, args);
    }

}
