package com.deizon.frydasignagesoftware.exception;

import com.deizon.services.exception.BaseException;

public class MissingDeployDataException extends BaseException {

    public MissingDeployDataException() {
        super("Deploy data are missing!");
    }

    public MissingDeployDataException(String message) {
        super(message);
    }
}
