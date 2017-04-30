package com.isa.spring.mvc.exceptions.percontroller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Handled by @ResponseStatus")
public class AnnotatedException extends RuntimeException {

    public AnnotatedException() {
        super();
    }

    public AnnotatedException(String message) {
        super(message);
    }

    public AnnotatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
