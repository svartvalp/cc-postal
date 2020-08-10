package com.crashcourse.ms_navigation.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Double longitude;
    private Double latitude;
    private String address;
}
