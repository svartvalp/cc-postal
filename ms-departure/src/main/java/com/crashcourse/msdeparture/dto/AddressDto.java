package com.crashcourse.msdeparture.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressDto {
    private Long id;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;
    private String address;
}
