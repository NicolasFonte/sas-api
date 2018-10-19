package com.nicolas.sasapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TmdbClientException extends Exception {

    private static final String MESSAGE_FORMAT = "Error fetching TMDB API. Reason: %s";

    private final String message;
    private int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public TmdbClientException(String message) {
        this.message = message;
    }

    public TmdbClientException(String message, int httpCode) {
        this.message = message;
        this.httpCode = httpCode;
    }

    public String formattedMessage() {
        return String.format(MESSAGE_FORMAT, message);
    }

}
