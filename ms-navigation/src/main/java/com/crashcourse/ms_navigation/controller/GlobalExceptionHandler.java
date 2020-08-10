package com.crashcourse.ms_navigation.controller;

import com.crashcourse.ms_navigation.dto.ErrorMessageDto;
import com.crashcourse.ms_navigation.exception.InvalidCoordinateDataException;
import com.crashcourse.ms_navigation.exception.MaximumDistanceLimitationException;
import com.crashcourse.ms_navigation.exception.PointNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler({InvalidCoordinateDataException.class, MaximumDistanceLimitationException.class})
    public ResponseEntity<ErrorMessageDto> handleInvalidGeoInformationExceptions(RuntimeException exc) {
        var errorMessage = new ErrorMessageDto();
        errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());
        errorMessage.setMessage(exc.getLocalizedMessage());
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }


    @ResponseBody
    @ExceptionHandler(PointNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handlePointNotFountException(RuntimeException exc) {
        var errorMessage = new ErrorMessageDto();
        errorMessage.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        errorMessage.setMessage(exc.getLocalizedMessage());
        return ResponseEntity.status(errorMessage.getStatus()).body(errorMessage);
    }

}
