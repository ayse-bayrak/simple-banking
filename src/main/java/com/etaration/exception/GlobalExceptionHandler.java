package com.etaration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.management.relation.RoleNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BankAccountNotFoundException.class, InsufficientBalanceException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleAllException(Exception ex) {
        return new ResponseEntity<>("ERROR OCCURRED: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}

