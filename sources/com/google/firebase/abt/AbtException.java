package com.google.firebase.abt;

public class AbtException extends Exception {
    public AbtException(String message) {
        super(message);
    }

    public AbtException(String message, Exception cause) {
        super(message, cause);
    }
}
