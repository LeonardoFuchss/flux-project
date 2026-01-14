package com.leoprojects.flux.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationErrorDto> handle(MethodArgumentNotValidException ex) {
        List<ValidationErrorDto> errors = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> errors.add(new ValidationErrorDto(e.getDefaultMessage())));

        return errors;
    }
    @ExceptionHandler(FluxException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(FluxException ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(JWTVerificationException exception) {
        return exception.getMessage();
    }
}
