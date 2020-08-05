package com.crashcourse.msdeparture.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class DepartureDto {
    private Long id;
    private Long userId;
    @NotNull
    private AddressDto departurePoint;
    @NotNull
    private AddressDto arrivingPoint;
    private String type;
    private Boolean arrived;
    private Integer weight;
    private String description;
    private LocalDateTime departureDate;
    private LocalDateTime arrivingDate;
}

