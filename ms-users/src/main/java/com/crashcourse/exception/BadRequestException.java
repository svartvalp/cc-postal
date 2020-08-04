package com.crashcourse.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadRequestException extends Exception {
    public BadRequestException(String msg) {
        super(msg);
    }
}
