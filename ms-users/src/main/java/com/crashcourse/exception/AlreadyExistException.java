package com.crashcourse.exception;

public class AlreadyExistException extends Exception {
    public String getMessage() {
        return "Entity already exists";
    }
}
