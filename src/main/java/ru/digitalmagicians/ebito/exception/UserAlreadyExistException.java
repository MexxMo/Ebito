package ru.digitalmagicians.ebito.exception;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException() {
        super("User already exist");
    }
}
