package com.paypal.ewallet.wallet.config;

import com.paypal.ewallet.wallet.exception.WalletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice// allowing you to handle exceptions across the whole application in one global handling component.
// It can be used to define centralized exception handling. You can define methods to handle exceptions thrown by any controller in the application.
public class WalletControllerAdvice {

    @ExceptionHandler(WalletException.class)
    public ResponseEntity<?> handleUserException(WalletException exception){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("type",exception.getType());
        errorMap.put("message",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}