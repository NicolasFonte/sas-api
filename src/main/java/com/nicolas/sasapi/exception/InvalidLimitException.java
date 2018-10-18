package com.nicolas.sasapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Limit should be positive.")
public class InvalidLimitException extends Exception {

}
