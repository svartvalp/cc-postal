package com.example.gateway.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private Integer id;
    private Double longitude;
    private Double latitude;
    private String address;
}
