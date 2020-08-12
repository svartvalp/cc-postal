package com.crashcourse.msdeparture.controller;

import com.crashcourse.msdeparture.exception.CustomErrorResponse;
import com.crashcourse.msdeparture.exception.DepartureNotFoundException;
import com.crashcourse.msdeparture.service.MessageService;
import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final MessageService messageService;

    @ExceptionHandler({DepartureNotFoundException.class})
    public ResponseEntity<CustomErrorResponse> handleDepartureNotFoundException(DepartureNotFoundException exc) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("no.such.departure.code"), exc.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CustomErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("bad.request.code"),
                Joiner.on("; ").skipNulls().join(e.getBindingResult().getFieldErrors()
                        .stream()
                        .map(m -> String.format("%s - %s", m.getField(), m.getDefaultMessage()))
                        .collect(Collectors.toList()))
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
