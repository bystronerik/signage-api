/* Copyright: Erik Bystroň - Redistribution and any changes prohibited. */
package com.deizon.frydasignagesoftware.exception;

import com.deizon.services.exception.BaseException;

public class UnsupportedFileType extends BaseException {

    public UnsupportedFileType() {
        super("Provided unsupported file type!");
    }

    public UnsupportedFileType(String message) {
        super(message);
    }
}
