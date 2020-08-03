package com.crashcourse.exception;

public class NoSuchEntityException extends Exception {
    public String getMessage() {
        return "No such entity in database";
    }
}
