package com.leoprojects.flux.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* Validation errors (@Valid) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDto handleValidation(MethodArgumentNotValidException ex) {
        var fieldError = ex.getBindingResult().getFieldError();

        String message = fieldError != null
                ? fieldError.getDefaultMessage()
                : "Validation error";

        return new ValidationErrorDto(message);
    }

    /* Business rule errors */
    @ExceptionHandler(FluxException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDto handleFlux(FluxException ex) {
        return new ValidationErrorDto(ex.getMessage());
    }

    /* JWT errors */
    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ValidationErrorDto handleJwt(JWTVerificationException ex) {
        return new ValidationErrorDto("Invalid or expired token");
    }
}