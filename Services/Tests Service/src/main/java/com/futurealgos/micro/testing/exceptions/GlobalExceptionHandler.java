/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.exceptions;

import com.futurealgos.micro.testing.dto.response.ErrorResponse;
import com.futurealgos.micro.testing.dto.response.PayloadError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException exception) {
        return ErrorResponse.builder().code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage()).date(LocalDateTime.now())
                .description(exception.getLocalizedMessage()).build();
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse handleBadRequestException(NotFoundException exception) {
        return ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value()).
                message(exception.getMessage()).date(LocalDateTime.now())
                .description(exception.getLocalizedMessage()).build();
    }

    @ExceptionHandler(value = {InvalidPayloadFormatException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public PayloadError handleInvalidPayloadFormatException(InvalidPayloadFormatException exception) {

        return PayloadError.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .date(LocalDateTime.now())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleDefaultException(Exception exception) {
//        logger.error("Error: {}", exception.getMessage(), exception);
        exception.printStackTrace();
        return ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value()).
                message(exception.getMessage()).date(LocalDateTime.now())
                .description(exception.getLocalizedMessage()).build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse handleUnauthorizedExceptions(Exception exception) {

        return ErrorResponse.builder()
                .message(exception.getMessage())
                .date(LocalDateTime.now())
                .code(HttpStatus.UNAUTHORIZED.value())
                .description(exception.getLocalizedMessage())
                .build();
    }
}
