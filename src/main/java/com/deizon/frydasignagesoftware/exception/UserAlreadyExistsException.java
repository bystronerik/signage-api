package com.deizon.frydasignagesoftware.exception;

import com.deizon.services.exception.BaseException;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException() {
        super("A user with this username already exists");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
