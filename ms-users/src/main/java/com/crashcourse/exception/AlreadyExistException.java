package com.crashcourse.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyExistException extends Exception {
    public AlreadyExistException(String msg) {
        super(msg);
    }
}
