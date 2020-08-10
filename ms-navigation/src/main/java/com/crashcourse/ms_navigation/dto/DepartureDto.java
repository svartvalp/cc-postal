package com.crashcourse.ms_navigation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DepartureDto {
    private Long userId;
    private Long id;
    private AddressDto departurePoint;
    private AddressDto arrivingPoint;
    private LocalDateTime departureDate;
    private LocalDateTime arrivingDate;
    private UserDto addressee;
}
