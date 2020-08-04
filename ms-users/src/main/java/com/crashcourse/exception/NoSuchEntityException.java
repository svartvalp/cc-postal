package com.crashcourse.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchEntityException extends Exception {
    public NoSuchEntityException(String msg) {
        super(msg);
    }
}
