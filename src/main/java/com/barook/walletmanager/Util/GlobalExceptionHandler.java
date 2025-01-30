package com.barook.walletmanager.Util;
import com.barook.walletmanager.CustomeException.WalletTransactionException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@ControllerAdvice  // This annotation makes this class a global exception handler
public class GlobalExceptionHandler {
    Logger log = Logger.getLogger(this.getClass().getName());


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        Logger log = Logger.getLogger(this.getClass().getName() + " handleValidationException");


        StringBuilder errors = new StringBuilder();
        BindingResult result = ex.getBindingResult();
        for (FieldError fieldError : result.getFieldErrors()) {
            errors.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("\n");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }

    // this mehtod created to handle duplicated value for User nationalId
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleValidationException(DataIntegrityViolationException ex) {
        // DO NOT HAVE TWO METHOD FOR HANDLE SAME EXCEPTION YOU WILL GET ERROR IN SRARTING SERVER
        Logger log = Logger.getLogger(this.getClass().getName() + " handleValidationException");


        StringBuilder errors = new StringBuilder();
        String result = ex.getMessage();
        if (result.contains("user.UK5rni7sst5cu05f85kawcglq6s")) // the unique ness of the national id
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user with national id already exists");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }

    // this mehtod created to handle user id of wallet
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // DO NOT HAVE TWO METHOD FOR HANDLE SAME EXCEPTION YOU WILL GET ERROR IN SRARTING SERVER
        Logger log = Logger.getLogger(this.getClass().getName() + " IllegalArgumentException");


        StringBuilder errors = new StringBuilder();
        String result = ex.getMessage();
        if (result.contains("The given id must not be null")) // the unique ness of the national id
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The should sent the user id");
        }
        else if (result.contains("User id cannot be null.")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The Given User Id Does Not Exist");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }

    // this mehtod created to handle existance of user id of wallet
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        // DO NOT HAVE TWO METHOD FOR HANDLE SAME EXCEPTION YOU WILL GET ERROR IN SRARTING SERVER
        Logger log = Logger.getLogger(this.getClass().getName() + " ConstraintViolationException");


        StringBuilder errors = new StringBuilder();
        String result = ex.getMessage();
        if (result.contains("The given id must not be null")) // the unique ness of the national id
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The should sent the user id");
        }
        else if (result.contains("'must not be null', propertyPath=user")){
            //if the user does not exist in data base
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The Given User Id Does Not Exist or null");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage().toString());
    }

    // this mehtod created to handle existance of user id of wallet
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        // DO NOT HAVE TWO METHOD FOR HANDLE SAME EXCEPTION YOU WILL GET ERROR IN SRARTING SERVER
        Logger log = Logger.getLogger(this.getClass().getName() + " ConstraintViolationException");


        StringBuilder errors = new StringBuilder();
        String result = ex.getMessage();
//        if (ex.("because \"toWallet\" is null")) // the existance of the wallet in transaction
//        {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The toWallet id is not exist");
//        }

        if (result.contains("because \"toWallet\" is null")) // the existance of the wallet in transaction
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The toWallet id is not exist");
        }

        if (result.contains("because \"fromWallet\" is null")) // the existance of the wallet in transaction
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The fromWallet id is not exist");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage().toString());
    }

    @ExceptionHandler(WalletTransactionException.class)
    public ResponseEntity<String> handleWalletTransactionException(WalletTransactionException ex) {

        if (ex.getMessage().contains("Both wallets don't exist")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both wallets don't exist");

        }if (ex.getMessage().contains("fromWallet not found")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fromWallet not found");

        }if (ex.getMessage().contains("toWallet not found")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("toWallet not found");

        }
        // Return the exception message with HTTP status BAD_REQUEST
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    // this mehtod created to handle duplicated value for User nationalId
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//        // Check if the cause is a SQLIntegrityConstraintViolationException
//        if (ex.getCause() instanceof SQLException) {
//            SQLException sqlException = (SQLException) ex.getCause();
//
//            // MySQL error code 1062 is for duplicate entry
//            if (sqlException.getErrorCode() == 1062) {
//                // Handle the duplicate entry scenario
//                String errorMessage = "Duplicate entry error: " + sqlException.getMessage();
//                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        // Return a generic error message for other DataIntegrityViolationExceptions
//        return new ResponseEntity<>("Data Integrity Violation", HttpStatus.BAD_REQUEST);
//    }


//    @ExceptionHandler(value = {YourCustomException.class}) // Handle specific exception types
//    public ResponseEntity<ErrorResponse> handleYourCustomException(YourCustomException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.BAD_REQUEST.value(), // Or appropriate HTTP status
//                ex.getMessage(),
//                request.getDescription(false));
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Return appropriate HTTP status
//    }
//
//    @ExceptionHandler(value = {AnotherCustomException.class})
//    public ResponseEntity<ErrorResponse> handleAnotherCustomException(AnotherCustomException ex, WebRequest request) {
//        // ... similar logic for another exception type, potentially with a different HTTP status
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.NOT_FOUND.value(), // Or appropriate HTTP status
//                ex.getMessage(),
//                request.getDescription(false));
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//
//    @ExceptionHandler(value = {Exception.class}) // Handle all other exceptions (as a last resort)
//    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "An unexpected error occurred.", // Generic message (don't expose internal details in production)
//                request.getDescription(false));
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
