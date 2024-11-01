package com.charlesdev.techtest.exception;

public class CarNotAvailableException extends Exception {
    public CarNotAvailableException(String message) {
        super("Car not available: " + message);
    }
}
