package com.isa.spring.mvc.exceptions.global.advice;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class BasicControllerAdvice {
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
        return "global/error";
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
        modelAndView.setViewName("global/errorWithModel");
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

    /**
     * Catch all exception handler
     * - Return to a view
     * - Model is populated
     * - Response status code is 200 (OK)
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it
        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
            throw exception;

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("global/error");
        return mav;
    }
}
