package com.deizon.frydasignagesoftware.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.List;

public class MissingDeployDataException extends RuntimeException
        implements GraphQLError, APIException {

    public MissingDeployDataException() {
        super("Deploy data are missing!");
    }

    public MissingDeployDataException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
