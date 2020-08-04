package com.crashcourse.controller;

import com.crashcourse.exception.*;
import com.crashcourse.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final MessageService messageService;

    @ExceptionHandler({NoSuchEntityException.class})
    public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(NoSuchEntityException e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("no.such.entity.code"),
                messageService.getMessage(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomErrorResponse> handleAllOtherExceptions(Exception e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("internal.error.code"),
                messageService.getMessage(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<CustomErrorResponse> handleAlreadyExists(AlreadyExistException e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("already.exists.code"),
                messageService.getMessage(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({BadConvertException.class})
    public ResponseEntity<CustomErrorResponse> handleBadConvert(BadConvertException e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("bad.convert.code"),
                messageService.getMessage(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<CustomErrorResponse> handleBadRequest(BadRequestException e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("bad.request.code"),
                messageService.getMessage(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
