package com.crashcourse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull
    private String login;
    private String passportNumber;
    @JsonProperty(value = "password", access = Access.WRITE_ONLY)
    @NotNull
    private String password;
    private AddressDto address;
}