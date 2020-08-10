package com.example.gateway.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String login;
    private String passportNumber;
    private String password;
    private AddressDto address;
}