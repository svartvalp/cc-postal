package com.crashcourse.ms_navigation.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private AddressDto userAddress;
}
