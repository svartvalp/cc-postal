package com.crashcourse.msdeparture.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class DepartureDto {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private AddressDto departurePoint;
    @NotNull
    private AddressDto arrivingPoint;
    @Size(max = 30)
    private String type;
    private Boolean arrived;
    private Integer weight;
    @Size(max = 255)
    private String description;
    private LocalDateTime departureDate;
    private LocalDateTime arrivingDate;
    private UserDto addressee;
}

