package com.bookmania.BookMania.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistException(EmailAlreadyExistException e,
                                                              WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
                request.getDescription(false));


        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<?> handleEmailNotValidException(EmailNotValidException e,
                                                              WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
                request.getDescription(false));


        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> handlePasswordNotMatchException(PasswordNotMatchException e,
                                                          WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
                request.getDescription(false));


        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }
}
