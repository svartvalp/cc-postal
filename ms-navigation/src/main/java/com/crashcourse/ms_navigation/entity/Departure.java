package com.crashcourse.ms_navigation.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Departure {
    @Id
    private String GUID;
    private Long departureId;
    private Long userId;
    private Address departurePoint;
    private Address arrivingPoint;
    private LocalDateTime departureDate;
    private LocalDateTime arrivingDate;
}
