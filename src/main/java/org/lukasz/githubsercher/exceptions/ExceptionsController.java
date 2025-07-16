package org.lukasz.githubsercher.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionsController {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public CustomError userNotFoundException(UserNotFoundException ex) {
        return new CustomError(NOT_FOUND.value(), ex.getMessage());
    }
    @ExceptionHandler(ResponseStatusException.class)
    public CustomError handleResponseStatusException(ResponseStatusException ex) {
        return new CustomError(ex.getStatusCode().value(), ex.getMessage());
    }
}

