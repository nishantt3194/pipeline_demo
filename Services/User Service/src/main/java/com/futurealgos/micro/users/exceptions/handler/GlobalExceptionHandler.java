/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.exceptions.handler;

import com.futurealgos.micro.users.exceptions.exceptions.AlreadyExistsException;
import com.futurealgos.micro.users.exceptions.exceptions.InvalidIdentifierFormat;
import com.futurealgos.micro.users.exceptions.exceptions.NotFoundException;
import com.futurealgos.micro.users.exceptions.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.InputMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundExceptions(NotFoundException ne, WebRequest request) {
        System.out.println(ne.getMessage());
        return new ResponseEntity<>(ne.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidIdentifierFormat.class)
    public ResponseEntity<String> handleInvalidIdentifierFormatExceptions(InvalidIdentifierFormat ne, WebRequest request) {
        System.out.println(ne.getMessage());
        return new ResponseEntity<>(ne.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InputMismatchException.class)
    public ResponseEntity<String> handInputMismatchException(InputMismatchException ie, WebRequest request) {
        return new ResponseEntity<>(ie.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExistsExceptions(AlreadyExistsException ae, WebRequest request) {

        return new ResponseEntity<>(ae.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedExceptions(UnauthorizedException ue, WebRequest request) {
        return new ResponseEntity<>(ue.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception e, WebRequest request) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
