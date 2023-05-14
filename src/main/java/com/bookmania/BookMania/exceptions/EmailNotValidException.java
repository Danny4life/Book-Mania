package com.bookmania.BookMania.exceptions;

public class EmailNotValidException extends RuntimeException {
    public EmailNotValidException(String message) {
        super(message);
    }
}
