/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.exception;

import com.deizon.services.exception.BaseException;

public class BadTokenException extends BaseException {

    public BadTokenException() {
        super("Supplied authentication token is invalid or expired");
    }

    public BadTokenException(String message) {
        super(message);
    }
}
