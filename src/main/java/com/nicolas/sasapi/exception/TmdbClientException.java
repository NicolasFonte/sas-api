package com.nicolas.sasapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "TMDb API is down or not behaving correctly")
public class TmdbClientException extends Exception {

    private final String message;

    public TmdbClientException(String message) {
        this.message = message;
    }
}
