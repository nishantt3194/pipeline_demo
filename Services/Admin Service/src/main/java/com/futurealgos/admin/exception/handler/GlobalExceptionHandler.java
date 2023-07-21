/*
 * Copyright (c) 2021. Future Algorithms Private Limited.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

package com.futurealgos.admin.exception.handler;


import com.futurealgos.admin.dto.response.ErrorDto;
import com.futurealgos.admin.exception.exceptions.AlreadyExistsException;
import com.futurealgos.admin.exception.exceptions.NotFoundException;
import com.futurealgos.admin.exception.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.InputMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundExceptions(NotFoundException ne, WebRequest request) {

        ErrorDto errorDto = ErrorDto.builder()
                .error(ne.getMessage())
                .timeStamp(LocalDate.now().toString())
                .status(HttpStatus.NOT_FOUND.toString())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InputMismatchException.class)
    public ResponseEntity<ErrorDto> handInputMismatchException(InputMismatchException ie, WebRequest request) {

        ErrorDto errorDto = ErrorDto.builder()
                .error(ie.getMessage())
                .timeStamp(LocalDate.now().toString())
                .status(HttpStatus.BAD_REQUEST.toString())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleAlreadyExistsExceptions(AlreadyExistsException ae, WebRequest request) {

        ErrorDto errorDto = ErrorDto.builder()
                .error(ae.getMessage())
                .timeStamp(LocalDate.now().toString())
                .status(HttpStatus.CONFLICT.toString())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDto> handleUnauthorizedExceptions(UnauthorizedException ue, WebRequest request) {

        ErrorDto errorDto = ErrorDto.builder()
                .error(ue.getMessage())
                .timeStamp(LocalDate.now().toString())
                .status(HttpStatus.UNAUTHORIZED.toString())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleOtherExceptions(Exception e, WebRequest request) {

        ErrorDto errorDto = ErrorDto.builder()
                .error(e.getMessage())
                .timeStamp(LocalDate.now().toString())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .build();
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(errorDto);
    }
}
