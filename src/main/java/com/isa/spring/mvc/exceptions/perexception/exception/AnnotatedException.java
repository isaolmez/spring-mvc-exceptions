package com.isa.spring.mvc.exceptions.perexception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Cannot find the resource")
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
