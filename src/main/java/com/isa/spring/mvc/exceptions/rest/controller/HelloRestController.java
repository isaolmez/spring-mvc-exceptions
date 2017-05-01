package com.isa.spring.mvc.exceptions.rest.controller;

import com.isa.spring.mvc.exceptions.global.exception.AnnotatedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@RestController
public class HelloRestController {

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
        throw new NumberFormatException("Will be handled by the @ExceptionHandler");
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

    /**
     * @ResponseStatus on @RequestMapping method does not handle the exception.
     * It would work if the exception was not thrown
     */
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    @GetMapping("/hello7")
    public void throwAnotherRuntimeException() {
        throw new RuntimeException("Will be handled by the catch all @ExceptionHandler");
    }

    /**
     * @ResponseStatus on @RequestMapping method sets the response code.
     */
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @GetMapping("/hello8")
    public void setResponseStatus() {
    }
}
