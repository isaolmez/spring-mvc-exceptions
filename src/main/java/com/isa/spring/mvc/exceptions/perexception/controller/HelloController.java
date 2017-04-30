package com.isa.spring.mvc.exceptions.perexception.controller;

import com.isa.spring.mvc.exceptions.perexception.exception.AnnotatedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String throwAnnotatedException() {
        throw new AnnotatedException("Will be handled by the @ResponseStatus");
    }

    @GetMapping("/hello2")
    public String throwUnannotatedException() {
        throw new RuntimeException("Will not be handled");
    }
}
