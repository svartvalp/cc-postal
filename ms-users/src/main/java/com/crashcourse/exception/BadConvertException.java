package com.crashcourse.exception;

public class BadConvertException extends Exception {
    @Override
    public String getMessage() {
        return "An error has occurred while converting";
    }
}
