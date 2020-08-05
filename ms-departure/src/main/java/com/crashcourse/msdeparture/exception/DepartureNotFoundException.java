package com.crashcourse.msdeparture.exception;

public class DepartureNotFoundException extends RuntimeException {
    public DepartureNotFoundException(String message) {
        super(message);
    }
}
