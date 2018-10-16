package com.nicolas.sasapi.controller.validation;

import java.util.Date;
import lombok.Getter;

@Getter
class ErrorDetails {

    private Date timestamp;
    private String message;

    ErrorDetails(Date timestamp, String message) {
        super();
        this.timestamp = timestamp;
        this.message = message;
    }
}
