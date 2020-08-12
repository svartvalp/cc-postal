package com.crashcourse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    private Integer id;
    @Size(max = 50)
    private String firstName;
    @Size(max = 50)
    private String middleName;
    @Size(max = 50)
    private String lastName;
    @NotNull
    @Size(max = 50)
    private String login;
    @Size(max = 15)
    private String passportNumber;
    @JsonProperty(value = "password", access = Access.WRITE_ONLY)
    @Size(max = 50)
    private String password;
    private AddressDto address;
}