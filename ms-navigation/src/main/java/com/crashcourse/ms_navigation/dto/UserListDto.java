package com.crashcourse.ms_navigation.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserListDto {
    private String departureId;
    private List<UserDto> users;
}
