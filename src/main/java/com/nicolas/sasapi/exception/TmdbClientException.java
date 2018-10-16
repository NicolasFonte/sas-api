package com.nicolas.sasapi.exception;

public class TmdbClientException extends Exception {

    private static final String MESSAGE_FORMAT = "Error fetching TMDB API. Reason: %s";

    private final String message;

    public TmdbClientException(String message) {
        this.message = message;
    }

    public String formattedMessage() {
        return String.format(MESSAGE_FORMAT, message);
    }
}
