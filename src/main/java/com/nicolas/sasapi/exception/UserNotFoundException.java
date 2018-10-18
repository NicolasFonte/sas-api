package com.nicolas.sasapi.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        this.userId = userId;
    }
}
