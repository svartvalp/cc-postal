package com.crashcourse.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Integer id;
    private Double longitude;
    private Double latitude;
    private String address;
}
