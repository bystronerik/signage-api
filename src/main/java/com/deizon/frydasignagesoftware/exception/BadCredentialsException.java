/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.exception;

import com.deizon.services.exception.BaseException;

public class BadCredentialsException extends BaseException {

    public BadCredentialsException() {
        super("Bad username or password!");
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
