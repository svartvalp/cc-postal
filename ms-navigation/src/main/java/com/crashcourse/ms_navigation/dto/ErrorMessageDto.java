package com.crashcourse.ms_navigation.dto;

import lombok.Data;

@Data
public class ErrorMessageDto {
    private int status;
    private String message;
}
