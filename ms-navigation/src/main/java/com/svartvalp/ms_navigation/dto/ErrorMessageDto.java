package com.svartvalp.ms_navigation.dto;

import lombok.Data;

@Data
public class ErrorMessageDto {
    private int status;
    private Object message;
}
