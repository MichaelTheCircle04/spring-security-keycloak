package com.mtrifonov.client.app.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @Mikhail Trifonov
 */
@ControllerAdvice
public class HttpClientErrorExceptionForbiddenAdvice {
    
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public String HttpClientErrorExceptionForbidden(HttpClientErrorException.Forbidden e) {
        return "access-denied";
    }
    
}
