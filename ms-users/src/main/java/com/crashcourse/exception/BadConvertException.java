package com.crashcourse.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadConvertException extends Exception {
    public BadConvertException(String msg) {
        super(msg);
    }

}
