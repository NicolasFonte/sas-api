package com.nicolas.sasapi.controller.responsehandler;

import java.util.Date;
import lombok.Getter;

@Getter
class ErrorDetails {

    private Date timestamp;
    private String message;

    ErrorDetails(String message) {
        super();
        this.message = message;
        this.timestamp = new Date();
    }
}
