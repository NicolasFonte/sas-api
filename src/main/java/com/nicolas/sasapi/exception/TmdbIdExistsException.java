package com.nicolas.sasapi.exception;

import lombok.Getter;

@Getter
public class TmdbIdExistsException extends Exception {

    private final Long tmdbId;

    public TmdbIdExistsException(Long tmdbId) {
        this.tmdbId = tmdbId;
    }
}
