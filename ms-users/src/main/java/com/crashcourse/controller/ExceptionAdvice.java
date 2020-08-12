package com.crashcourse.controller;

import com.crashcourse.exception.*;
import com.crashcourse.service.MessageService;
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

    @ExceptionHandler({NoSuchEntityException.class})
    public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(NoSuchEntityException e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("no.such.entity.code"), e.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomErrorResponse> handleAllOtherExceptions(Exception e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("internal.error.code"), e.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<CustomErrorResponse> handleAlreadyExistsException(AlreadyExistException e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("already.exists.code"), e.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadConvertException.class})
    public ResponseEntity<CustomErrorResponse> handleBadConvertException(BadConvertException e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("bad.convert.code"), e.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<CustomErrorResponse> handleBadRequestException(BadRequestException e) {
        CustomErrorResponse error = new CustomErrorResponse(
                messageService.getMessage("bad.request.code"), e.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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
