package com.isa.spring.mvc.exceptions.global.controller;

import com.isa.spring.mvc.exceptions.global.exception.AnnotatedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public void throwAnnotatedException() {
        throw new AnnotatedException("Will be handled by the @ResponseStatus");
    }

    @GetMapping("/hello2")
    public void throwIllegalArgumentException() {
        throw new IllegalArgumentException("Will be handled by the @ExceptionHandler");
    }

    @GetMapping("/hello3")
    public void throwIllegalStateException() {
        throw new IllegalStateException("Will be handled by the @ExceptionHandler");
    }

    @GetMapping("/hello4")
    public void throwNullPointerException() {
        throw new NullPointerException("Will be handled by the @ExceptionHandler");
    }

    @GetMapping("/hello5")
    public void throwNumberFormatException() {
        throw new NumberFormatException("Will be handled by the @ExceptionHandler");
    }

    @GetMapping("/hello6")
    public void throwRuntimeException() {
        throw new RuntimeException("Will be handled by the catch all @ExceptionHandler");
    }

    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    @GetMapping("/hello7")
    public void throwAnotherRuntimeException() {
        throw new RuntimeException("Will be handled by the catch all @ExceptionHandler");
    }
}
