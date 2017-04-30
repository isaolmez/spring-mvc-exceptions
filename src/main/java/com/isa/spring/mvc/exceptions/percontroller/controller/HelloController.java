package com.isa.spring.mvc.exceptions.percontroller.controller;

import com.isa.spring.mvc.exceptions.percontroller.exception.AnnotatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Controller
public class HelloController {

    /**
     * RequestMapping Methods
     */

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
        throw new RuntimeException("Will not be handled");
    }

    /**
     * @ResponseStatus on @RequestMapping method does not have any effect.
     * It does not map exceptions thrown from this method to the specified status code.
     */
    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    @GetMapping("/hello7")
    public void throwAnotherRuntimeException() {
        throw new RuntimeException("Will not be handled");
    }

    /**
     * ExceptionHandler Methods
     */

    /**
     * - Sets the response status code as specified
     * - Response body is null
     * - Error reason is as specificed
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Handled by @ExceptionHandler")
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException() {
        // Do nothing
    }

    /**
     * - Return to the view
     * - Model is null
     * - Response status is 200 (OK), change it 400
     */
    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(HttpServletResponse response) {
        // Because we are handling the error, the server thinks everything is
        // OK, so the status is 200. So let's set it to something else.
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return "perController/error";
    }

    /**
     * - Response status is 200 (OK)
     * - Return to a view
     * - Model is populated
     * Model cannot be a parameter in the method signature. Construct a new one
     */
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException(NullPointerException exception, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "Handled by @ExceptionHandler and forwarded to view");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName("perController/errorWithModel");
        return modelAndView;
    }

    /**
     * - Directly write to the response
     * - View is null
     * - Response status code is 400
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Handled by @ExceptionHandler and written to response body");
    }
}
