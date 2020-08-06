package com.crashcourse.ms_navigation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserListDto {
    private String departureId;
    private List<UserDto> users;
}
