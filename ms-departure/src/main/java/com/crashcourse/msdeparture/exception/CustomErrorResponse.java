package com.crashcourse.msdeparture.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomErrorResponse {
    private String errorCode;
    private String errorMessage;
}
