package com.sportsradar.exception;

public class InvalidInputException extends Exception{
    public InvalidInputException(String message) {
        super(String.format("Invalid input exception: %", message));
    }
}
