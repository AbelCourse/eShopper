package edu.miu.cs489.eshopper.exception;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}