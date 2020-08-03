package com.crashcourse.controller;

import com.crashcourse.exception.AlreadyExistException;
import com.crashcourse.exception.BadConvertException;
import com.crashcourse.exception.CustomErrorResponse;
import com.crashcourse.exception.NoSuchEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({NoSuchEntityException.class})
    public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(NoSuchEntityException e) {
        CustomErrorResponse error = new CustomErrorResponse("NOT_FOUND_ERROR", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomErrorResponse> handleAllOtherExceptions(Exception e) {
        CustomErrorResponse error = new CustomErrorResponse("INTERNAL_ERROR", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<CustomErrorResponse> handleAlreadyExists(AlreadyExistException e) {
        CustomErrorResponse error = new CustomErrorResponse("ALREADY_EXIST", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({BadConvertException.class})
    public ResponseEntity<CustomErrorResponse> handleBadConvert(BadConvertException e) {
        CustomErrorResponse error = new CustomErrorResponse("BAD_CONVERT", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
