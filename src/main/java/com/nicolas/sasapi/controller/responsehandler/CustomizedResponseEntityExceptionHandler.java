package com.nicolas.sasapi.controller.responsehandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nicolas.sasapi.exception.TmdbClientException;
import com.nicolas.sasapi.exception.TmdbIdExistsException;
import com.nicolas.sasapi.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getCause() instanceof InvalidFormatException) {
            ErrorDetails details = new ErrorDetails(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(details);
        }

        ErrorDetails details = new ErrorDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
    }

    @ExceptionHandler(TmdbClientException.class)
    public final ResponseEntity<ErrorDetails> handleClientException(TmdbClientException ex) {
        ErrorDetails details = new ErrorDetails(ex.formattedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(details);
    }

    @ExceptionHandler(TmdbIdExistsException.class)
    public final ResponseEntity<ErrorDetails> handleTmdbIdExistsException(TmdbIdExistsException ex) {
        String message = String.format("Tmdb Movie %s already selected as favorite", ex.getTmdbId());
        ErrorDetails details = new ErrorDetails(message);
        return ResponseEntity.status(HttpStatus.OK).body(details);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex) {
        String message = String.format("User %s not exists in the system", ex.getUserId());
        ErrorDetails details = new ErrorDetails(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(details);
    }

}